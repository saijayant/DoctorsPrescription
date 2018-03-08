package com.sai_jayant.doctorprescription;

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class DbHelper extends SQLiteOpenHelper {
    private static final String DBNAME = "Info.db";
    private static final int DBVERSION = 1;
    public static final String TABLENAME = "Information";
    private static final String MEDICINENAME = "medicine_name";
    private static final String MEDICINEDESC = "medicine_desc";
    private static final String DAYTIMEAFTERFOOD = "day_time_after_food";
    private static final String DAYTIMEBEFOREFOOD = "day_time_before_food";
    private static final String NIGHTTIMEAFTERFOOD = "night_time_after_food";
    private static final String NIGHTTIMEBEFOREFOOD = "night_time_before_food";
    private static final String ISSELECTED = "isselected";
    private static final String MEDICINETYPE = "medicine_type";
    private static final String ID = "id";


    public DbHelper(Context context) {
        super(context, DBNAME, null, DBVERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLENAME+"("+ MEDICINENAME+" TEXT,"+ MEDICINEDESC+" TEXT,"
                + DAYTIMEAFTERFOOD+" TEXT,"
                + DAYTIMEBEFOREFOOD+" TEXT,"
                + NIGHTTIMEAFTERFOOD+" TEXT,"
                + NIGHTTIMEBEFOREFOOD+" TEXT,"
                + ISSELECTED+" TEXT,"
                + MEDICINETYPE+" TEXT,"
        +ID+" INTEGER PRIMARY KEY AUTOINCREMENT);");

    }

    public void insertData(SQLiteDatabase db, String medicname, String medicdesc, String dayafterfood, String daybeforefood,
                           String nightafterfood, String nightbeforefood, String isselected, String medictype){
        ContentValues values = new ContentValues();
        values.put(MEDICINENAME, medicname);
        values.put(MEDICINEDESC, medicdesc);
        values.put(DAYTIMEAFTERFOOD, dayafterfood);
        values.put(DAYTIMEBEFOREFOOD, daybeforefood);
        values.put(NIGHTTIMEAFTERFOOD, nightafterfood);
        values.put(NIGHTTIMEBEFOREFOOD, nightbeforefood);
        values.put(ISSELECTED, isselected);
        values.put(MEDICINETYPE, medictype);
        db.insert(TABLENAME, null, values);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public ArrayList<Medicine> GetAllData(SQLiteDatabase sqLiteDatabase) {
        ArrayList<Medicine> result = new ArrayList<>();
        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLENAME,null);

        while (c.moveToNext()) {
            result.add(new Medicine(
                            c.getString(c.getColumnIndex(MEDICINENAME)),
                            c.getString(c.getColumnIndex(MEDICINEDESC)),
                            c.getString(c.getColumnIndex(DAYTIMEAFTERFOOD)),
                            c.getString(c.getColumnIndex(DAYTIMEBEFOREFOOD)),
                            c.getString(c.getColumnIndex(NIGHTTIMEAFTERFOOD)),
                            c.getString(c.getColumnIndex(NIGHTTIMEBEFOREFOOD)),
                            c.getString(c.getColumnIndex(ISSELECTED)),
                            c.getString(c.getColumnIndex(MEDICINETYPE)),
                            c.getInt(c.getColumnIndex(ID))


                    )
            );
        }
        c.close();
        return result;
    }
    public void DeleteRecords(SQLiteDatabase sqLiteDatabase,int id){
        String sql = "DELETE FROM " +TABLENAME+ " WHERE " +ID+ "=" +id;
        sqLiteDatabase.execSQL(sql);
    }

}
