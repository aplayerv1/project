package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class Sql extends SQLiteOpenHelper {
    private static final String DB_NAME = "lab05";
    private static final String DB_TABLE = "tasks";
    private static final int DB_VERSION = 1;
    private static final String ID_COL = "id";
    private static final String TASK_COL = "name";
    private static final String TASK_URGCOL = "urg";
    private static boolean TASK_URG = false;
    private static int i = 0;

    public Sql(Context context, int version) {
        super(context, DB_NAME, null, version);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + DB_TABLE + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TASK_COL + " TEXT," + TASK_URGCOL + " INTEGER)";
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
    public void addTasks(String task, Boolean URG) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(TASK_COL, task);
        values.put(TASK_URGCOL, URG?1:0);

        db.insert(DB_TABLE, null, values);

        db.close();
    }
    public ArrayList<Data> getTasks(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cr = db.rawQuery("SELECT * FROM "+ DB_TABLE, null);
        ArrayList<Data> arr = new ArrayList<>();

//        if (cr.moveToFirst()){
//            do {
//                arr.add(new Data(cr.getString(1),
//                        cr.getInt(2)==1,cr.getInt(0)));
//            } while (cr.moveToNext());
//        }

        cr.close();
        return arr;
    }
    public void removeTask(int i){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ DB_TABLE + " WHERE " + ID_COL + " = " + i);
        db.close();
    }

}