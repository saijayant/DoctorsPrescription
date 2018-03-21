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
    private static final String MED_ID = "med_id";
    private static final String ID = "id";
    public static final String PATIENT_TABLE = "patientinfo";
    public static final String PATIENTNAME = "patient_name";
    public static final String GENDER = "gender";
    public static final String AGE = "age";
    public static final String WEIGHT = "weight";
    public static final String MOBILE = "mobile";
    public static final String ADDRESS = "address";
    public static final String TIME = "time";
    public static final String MEDICINE = "medicine";



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
                + MED_ID+" TEXT,"
        +ID+" INTEGER PRIMARY KEY AUTOINCREMENT);");
        db.execSQL("CREATE TABLE "+PATIENT_TABLE+"("+ PATIENTNAME+" TEXT,"+ GENDER+" TEXT,"
                + AGE+" TEXT,"
                + WEIGHT+" TEXT,"
                + MOBILE+" TEXT,"
                + ADDRESS+" TEXT,"
                + TIME+" TEXT,"
                + MEDICINE+" TEXT,"
                +ID+" INTEGER PRIMARY KEY AUTOINCREMENT);");

    }

    public void insertData(SQLiteDatabase db, String medicname, String medicdesc, String dayafterfood, String daybeforefood,
                           String nightafterfood, String nightbeforefood, String isselected, String medictype, String output){
        ContentValues values = new ContentValues();
        values.put(MEDICINENAME, medicname);
        values.put(MEDICINEDESC, medicdesc);
        values.put(DAYTIMEAFTERFOOD, dayafterfood);
        values.put(DAYTIMEBEFOREFOOD, daybeforefood);
        values.put(NIGHTTIMEAFTERFOOD, nightafterfood);
        values.put(NIGHTTIMEBEFOREFOOD, nightbeforefood);
        values.put(ISSELECTED, isselected);
        values.put(MEDICINETYPE, medictype);
        values.put(MED_ID, output);
        db.insert(TABLENAME, null, values);

    }




    public void insertPatientDetails(SQLiteDatabase sqLiteDatabase,String name,String gender,
                                     String age,String weight,String mobile,String address,
                                     String time,String medic){
        ContentValues values = new ContentValues();
        values.put(PATIENTNAME, name);
        values.put(GENDER, gender);
        values.put(AGE, age);
        values.put(WEIGHT, weight);
        values.put(MOBILE, mobile);
        values.put(ADDRESS, address);
        values.put(TIME, time);
        values.put(MEDICINE, medic);
        sqLiteDatabase.insert(PATIENT_TABLE, null, values);


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
                            c.getString(c.getColumnIndex(MED_ID)),
                            c.getInt(c.getColumnIndex(ID))


                    )
            );
        }
        c.close();
        return result;
    }



    public ArrayList<Patient> GetAllPatient(SQLiteDatabase sqLiteDatabase) {
        ArrayList<Patient> result = new ArrayList<>();
        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM "+PATIENT_TABLE,null);

        while (c.moveToNext()) {
            result.add(new Patient(
                            c.getString(c.getColumnIndex(PATIENTNAME)),
                            c.getString(c.getColumnIndex(GENDER)),
                            c.getString(c.getColumnIndex(AGE)),
                            c.getString(c.getColumnIndex(WEIGHT)),
                            c.getString(c.getColumnIndex(MOBILE)),
                            c.getString(c.getColumnIndex(ADDRESS)),
                            c.getString(c.getColumnIndex(TIME)),
                            c.getString(c.getColumnIndex(MEDICINE)),
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

    public void DeletePatientRecords(SQLiteDatabase sqLiteDatabase,int id){
        String sql = "DELETE FROM " +PATIENT_TABLE+ " WHERE " +ID+ "=" +id;
        sqLiteDatabase.execSQL(sql);
    }

}
