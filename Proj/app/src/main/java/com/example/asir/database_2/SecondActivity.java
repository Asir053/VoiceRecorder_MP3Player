package com.example.asir.database_2;

import android.database.Cursor;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {
    Button deleteit;
    EditText iddeletefield;
    public static final String TAG = "ListDataActivity";
    Databasehandler dbhelper;
    private ListView listView;
    TextView display;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout_two);

        display = (TextView) findViewById(R.id.display2);
        display.setMovementMethod(new ScrollingMovementMethod());

        dbhelper = new Databasehandler(this);

        deleteit = (Button) findViewById(R.id.btnDeleteIt);
        iddeletefield = (EditText) findViewById(R.id.Deletefield);

        List<Records> myRecordList=dbhelper.getAllRecords();
        String result = "";
        for(Records myRecords : myRecordList)
        {
            result +="Id: "+ myRecords.getId()+"\nName: "+myRecords.getRecord();
            result +="\n";
            Log.d("Result",result);
        }
        if(myRecordList.size()  == 0)
        {
            result = "No Recordings to display.";
        }
        display.setText(result);

        deleteit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String deleteIdValue = iddeletefield.getText().toString();
                if(deleteIdValue.equals(""))
                {
                    Toast.makeText (getApplicationContext(),"Information Missing", Toast.LENGTH_SHORT).show();
                }
                else{
                    dbhelper.deleteRecords(Integer.parseInt(deleteIdValue));
                    Toast.makeText (getApplicationContext(),deleteIdValue+" is deleted",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}
