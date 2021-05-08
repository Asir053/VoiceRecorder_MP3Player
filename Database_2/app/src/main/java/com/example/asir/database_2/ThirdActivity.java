package com.example.asir.database_2;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {
    Button showit;
    EditText idNewfield;
    Databasehandler dbhelper1;
    TextView display1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout_one);

        dbhelper1 = new Databasehandler(this);
        display1 = (TextView) findViewById(R.id.display3);

        showit = (Button) findViewById(R.id.btnShowitSingle);
        idNewfield = (EditText) findViewById(R.id.idfield1);

        showit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idvalue = idNewfield.getText().toString();
                    if(idvalue.equals(""))
                    {
                        Toast.makeText (getApplicationContext(),"Information Missing",Toast.LENGTH_SHORT).show();
                        display1.setText("Information Missing");
                    }
                    else{
                        Records myrecord=dbhelper1.getSingleRecord(Integer.parseInt(idvalue));
                        String result = "";
                        if(myrecord == null)
                        {
                            result = "No Recordings to display.";
                        }
                        else{
                            result +="Id: "+ myrecord.getId()+" Name: "+myrecord.getRecord();
                        }
                        display1.setText(result);
                        Toast.makeText (getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                        Log.d("res:",result);
                    }
            }
        });
    }
}
