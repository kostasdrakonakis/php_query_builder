package com.example.kostas.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.kostas.databasehandlers.DataBaseHelper;

public class LoginDataBaseAdapter {

    static final String DATABASE_NAME = "login.db";
    static final int DATABASE_VERSION = 1;
    static final int NAME_COLUMN = 1;

    public static final String DATABASE_CREATE = "create table "+"LOGIN"+
            "( " +"ID"+" integer primary key autoincrement,"+ "USERNAME  text,PASSWORD text); ";

    public SQLiteDatabase db;
    private final Context context;
    private DataBaseHelper dbHelper;

    public  LoginDataBaseAdapter(Context _context){
        context = _context;
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public  LoginDataBaseAdapter open() throws SQLException{
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        db.close();
    }

    public  SQLiteDatabase getDatabaseInstance(){
        return db;
    }

    public void insertEntry(String userName,String password){
        ContentValues newValues = new ContentValues();
        newValues.put("USERNAME", userName);
        newValues.put("PASSWORD",password);

        db.insert("LOGIN", null, newValues);
    }
    public int deleteEntry(String UserName){
        String where="USERNAME=?";
        int numberOFEntriesDeleted= db.delete("LOGIN", where, new String[]{UserName}) ;
        return numberOFEntriesDeleted;
    }

    public String getSinlgeEntry(String userName){
        Cursor cursor=db.query("LOGIN", null, " USERNAME=?", new String[]{userName}, null, null, null);
        if(cursor.getCount()<1)
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password= cursor.getString(cursor.getColumnIndex("PASSWORD"));
        cursor.close();
        return password;
    }

    public void updateEntry(String userName,String password){

        ContentValues updatedValues = new ContentValues();
        updatedValues.put("USERNAME", userName);
        updatedValues.put("PASSWORD",password);

        String where="USERNAME = ?";
        db.update("LOGIN",updatedValues, where, new String[]{userName});
    }
}
