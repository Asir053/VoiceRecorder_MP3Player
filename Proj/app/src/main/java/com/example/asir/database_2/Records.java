package com.example.asir.database_2;

public class Records {
    int _id;
    String _record;

    public Records()
    {

    }
    public Records(int Id,String Record )
    {
        this._id=Id;
        this._record=Record;
    }

    public Records(String Record)
    {
        this._record=Record;
    }

    public int getId()
    {
        return this._id;
    }
    public void setId(int Id)
    {
        this._id=Id;
    }

    public String getRecord()
    {
        return this._record;
    }
    public void setRecord(String Record)
    {
        this._record=Record;
    }

}
