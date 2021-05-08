package com.example.asir.database_2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    Button btnRecord,btnStopRecording,btnPlay,btnStop,addTo,showIn,showsingle;
    String pathsave = "";
    EditText name,id;
    MediaRecorder mediaRecorder;
    MediaPlayer mediaPlayer;
    //TextView display;

    final int REQUEST_PERMISSION_CODE = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Databasehandler db=new Databasehandler(this);
        //display = (TextView) findViewById(R.id.displayTV);
        //display.setMovementMethod(new ScrollingMovementMethod());
        //name= findViewById(R.id.edit);
        //id= findViewById(R.id.idfield);


        if(!checkPermissionFromDevice())
        {
            requestPermission();
        }

        btnPlay = findViewById(R.id.btnStartPlaying);
        btnStop = findViewById(R.id.btnStopPlaying);
        btnRecord = findViewById(R.id.btnStartRecord);
        btnStopRecording = findViewById(R.id.btnStopRecord);
        addTo = findViewById(R.id.add);
        showIn = findViewById(R.id.show);
        showsingle = findViewById(R.id.btnShow1by1);


        if(checkPermissionFromDevice())
        {
            btnRecord.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if(checkPermissionFromDevice()) {


                        pathsave = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + UUID.randomUUID().toString() + "_audio_recorder.mp3";
                        File file = new File(pathsave,"_audio_recorder.mp3");
                        setupMediaRecorder();
                        try {
                            mediaRecorder.prepare();
                            mediaRecorder.start();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        btnPlay.setEnabled(false);
                        btnStop.setEnabled(false);
                        btnStopRecording.setEnabled(true);

                        Toast.makeText(MainActivity.this, "Recording...", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        requestPermission();
                    }

                }

            });

            btnStopRecording.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mediaRecorder.stop();
                    btnStopRecording.setEnabled(false);
                    btnPlay.setEnabled(true);
                    btnRecord.setEnabled(true);
                    btnStop.setEnabled(true);

                }
            });

            addTo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String namevalue = pathsave;
                    //Records r= new Records();
                    if(namevalue.equals(""))
                    {
                        Toast.makeText (getApplicationContext(),"Information Missing",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        //r.setRecord(namevalue);
                        db.addRecord(new Records(namevalue));
                        Toast.makeText (getApplicationContext(),"New Recording Added.",Toast.LENGTH_SHORT).show();
                    }
                }
            });

            showIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(MainActivity.this,SecondActivity.class);
                    startActivity(intent);
                    //this will show all records
                }
            });

            showsingle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(MainActivity.this,ThirdActivity.class);
                    startActivity(intent);
                    //this will show single record

//                    String idvalue = id.getText().toString();
//                    if(idvalue.equals(""))
//                    {
//                        Toast.makeText (getApplicationContext(),"Information Missing",Toast.LENGTH_SHORT).show();
//                    }
//                    else{
//
//
//
//                        Records myrecord=db.getSingleRecord(Integer.parseInt(idvalue));
//                        String result = "";
//                        if(myrecord == null)
//                        {
//                            result = "No contact to display.";
//                        }
//                        else{
//                            result +="Id: "+ myrecord.getId()+" Name: "+myrecord.getRecord();
//                        }
//                        display.setText(result);
//                        Toast.makeText (getApplicationContext(),result,Toast.LENGTH_SHORT).show();
//                        Log.d("res:",result);
//                    }
                }
            });

            btnPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnStop.setEnabled(true);
                    btnStopRecording.setEnabled(false);
                    btnRecord.setEnabled(false);

                    mediaPlayer = new MediaPlayer();
                    try{
                        mediaPlayer.setDataSource(pathsave);
                        mediaPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mediaPlayer.start();
                    Toast.makeText(MainActivity.this,"Playing",Toast.LENGTH_SHORT).show();
                }
            });

            btnStop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnStopRecording.setEnabled(false);
                    btnRecord.setEnabled(true);
                    btnStop.setEnabled(false);
                    btnPlay.setEnabled(true);

                    if(mediaPlayer!=null)
                    {
                        mediaPlayer.stop();
                        mediaPlayer.release();

                        setupMediaRecorder();
                    }
                }
            });
        }

        else
        {
            requestPermission();
        }
    }

       private void setupMediaRecorder() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(pathsave);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO
        },REQUEST_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case REQUEST_PERMISSION_CODE:
            {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(this,"Permission Granted", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this,"Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private boolean checkPermissionFromDevice() {
        int write_external_storage_result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int record_audio_result = ContextCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO);
        return write_external_storage_result == PackageManager.PERMISSION_GRANTED && record_audio_result == PackageManager.PERMISSION_GRANTED;
    }
}
