package com.example.marco.myfirstapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TextView;

/**
 * Created by Marco on 16/10/2017.
 */

public class DB_Controller extends SQLiteOpenHelper{
    public DB_Controller(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "TEST.db", factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE STUDENTS( ID INTEGER PRIMARY KEY AUTOINCREMENT, FIRSTNAME TEXT UNIQUE, LASTNAME TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS STUDENTS;");
        onCreate(db);
    }

    public void insert_student(String firstName, String lastName){
        ContentValues contentValues = new ContentValues();
        contentValues.put("FIRSTNAME", firstName);
        contentValues.put("LASTNAME", lastName);
        this.getWritableDatabase().insertOrThrow("STUDENTS", "", contentValues);
    }

    public void delete(String firstName){
        this.getWritableDatabase().delete("STUDENTS", "FIRSTNAME='"+firstName+"'",null);

    }

    public void update_student(String oldFirstName, String newFirstName){
        this.getWritableDatabase().execSQL("UPDATE STUDENTS SET FIRSTNAME ='"+ newFirstName+ "' WHERE FIRSTNAME='" + oldFirstName+"'");

    }

    public void list_all_studentds(TextView textView){
        Cursor cursor = this.getWritableDatabase().rawQuery("SELECT * FROM STUDENTS",null);
        textView.setText("");
        while  (cursor.moveToNext()){
            textView.append(cursor.getString(1) + " " +cursor.getString(2)+"\n");
        }
    }

}
