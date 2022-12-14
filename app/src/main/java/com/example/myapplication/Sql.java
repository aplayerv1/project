package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class Sql extends SQLiteOpenHelper {
    private static final String DB_NAME = "lab05";
    private static final String DB_TABLE = "tasks";
    private static final int DB_VERSION = 1;
    private static final String ID_COL = "id";
    private static final String TASK_COL = "name";
    private static final String TASK_URL = "URL";
    private static final String TASK_DES = "desc";
    private static final String TASK_FAV = "fav";
    private static boolean TASK_URG = false;
    private static int i = 0;

    public Sql(Context context, int version) {
        super(context, DB_NAME, null, version);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + DB_TABLE + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TASK_COL + " TEXT," + TASK_URL + " TEXT," +TASK_DES +" TEXT,"+ TASK_FAV +" INTEGER )";



        Log.d("TAG","SQL : "+query);
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
        onCreate(db);
    }
    public SQLiteDatabase getDB(){
        return this.getWritableDatabase();
    }
    public void addTasks(String task, String url, String desc, int i) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(TASK_COL, task);
        values.put(TASK_URL, url);
        values.put(TASK_DES, desc);
        values.put(TASK_FAV, i);
        Log.d("TAG","add Task"+values);
        db.insert(DB_TABLE, null, values);

        db.close();
    }
    public ArrayList<Data> getTasks(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cr = db.rawQuery("SELECT * FROM "+ DB_TABLE, null);
        ArrayList<Data> arr = new ArrayList<>();

        if (cr.moveToFirst()){
            do {
                arr.add(new Data(cr.getString(1),cr.getString(2), cr.getString(3),cr.getInt(4)));
            } while (cr.moveToNext());
        }
        cr.close();
        return arr;
    }
    public void removeTask(int i){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ DB_TABLE + " WHERE " + ID_COL + " = " + i);
        Log.d("TAG","DELETING DB");
        db.close();
    }

}