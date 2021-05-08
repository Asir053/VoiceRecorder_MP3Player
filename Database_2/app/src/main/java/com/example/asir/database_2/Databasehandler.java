package com.example.asir.database_2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Databasehandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="recordsManager";
    private static final String TABLE_NAME="recordings";
    private static final String KEY_ID="id";
    private static final String KEY_NAME="name";

    public Databasehandler(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        //Log.v("db created", "yes");
        String CREATE_RECORD_TABLE="CREATE TABLE "+ TABLE_NAME +"("
                + KEY_ID +" INTEGER PRIMARY KEY,"
                + KEY_NAME +" TEXT"+")";

        String sql= "CREATE TABLE RECORDINGS (ID INTEGER PRIMARY KEY," +
                "NAME TEXT)";

        db.execSQL(CREATE_RECORD_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        String sql = "DROP TABLE IF EXISTS Recordings";
        db.execSQL(sql);
        onCreate(db);
    }
    public void  addNewRecord(String a)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "INSERT INTO CONTACT(NAME)" +
                "VALUES('"+a+"')";

        //String qry = "INSERT INTO CONTACT(NAME,PHONENO)VALUES('XYZ','016')";
        //db.execSQL(query);

        ContentValues value=new ContentValues();
        value.put(KEY_NAME,a);

        db.insert(TABLE_NAME, null,value);

        db.close();
    }

    public void addRecord(Records rec)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "INSERT INTO CONTACT(NAME)" +
                "VALUES('"+rec.getRecord()+"')";

        //String qry = "INSERT INTO CONTACT(NAME,PHONENO)VALUES('XYZ','016')";
        //db.execSQL(query);

        ContentValues value=new ContentValues();
        value.put(KEY_NAME,rec.getRecord());

        db.insert(TABLE_NAME, null,value);

        db.close();

    }

    public Records getSingleRecord(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT ID,NAME FROM RECORDINGS WHERE ID = "+id;
        Cursor cursor = db.rawQuery(query, null);
        //Cursor cursor = db.query(TABLE_NAME, new String[] {KEY_ID,KEY_NAME,KEY_CONTACTNO}, "Id=?",new String[]{String.valueOf(id)} ,null, null,null, null);
        Records myRecords = null;
        if(cursor.moveToFirst())
        {
            myRecords=new Records(Integer.parseInt(cursor.getString(0)),cursor.getString(1));
        }

        return myRecords;
    }

    public List<Records> getAllRecords()
    {
        List<Records> myrecordList=new ArrayList<Records>();

        String selectquery="SELECT * FROM "+ TABLE_NAME;// where phoneno LIKE '017%'";

        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor=db.rawQuery(selectquery, null);

        if(cursor.moveToFirst())
        {
            do
            {
                Records records1= new Records(Integer.parseInt(cursor.getString(0)),cursor.getString(1));
                myrecordList.add(records1);
            }while(cursor.moveToNext());
        }

        return myrecordList;
    }

    public void updateRecords(Records rec)
    {
        SQLiteDatabase db=this.getWritableDatabase();

        String query = "UPDATE RECORDINGS SET name='abc' WHERE ID = "+rec.getId() ;
        db.execSQL(query);

		/*ContentValues value=new ContentValues();
		value.put(KEY_NAME, contact.getName());
		value.put(KEY_CONTACTNO,contact.getContactNumber());

		db.update(TABLE_NAME, value, KEY_ID+"=?", new String[]{String.valueOf(contact.getId())});
		*/

        db.close();
    }

    public void deleteRecords(int id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        String query = "DELETE from RECORDINGS WHERE ID="+id;
        db.execSQL(query);

        //	db.delete(TABLE_NAME, KEY_ID+"=?", new String[]{String.valueOf(id)});
        db.close();
    }

}
