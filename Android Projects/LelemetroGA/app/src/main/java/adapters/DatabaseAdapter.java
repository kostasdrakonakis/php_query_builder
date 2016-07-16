package adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import databasehandlers.DataBaseHelper;


public class DatabaseAdapter {
    static final String DATABASE_NAME = "login.db";
    static final int DATABASE_VERSION = 1;
    static final int NAME_COLUMN = 1;

    public static final String DATABASE_CREATE = "create table "+"LOGIN"+
            "( " +"ID"+" integer primary key autoincrement,"+ "USERNAME  text, PASSWORD text, DATE_KATATAKSIS text, DATE_APOLUSIS text, SEIRA int, ESSO text, DUNAMI text, OPLO text); ";

    public SQLiteDatabase db;
    private final Context context;
    private DataBaseHelper dbHelper;

    public DatabaseAdapter(Context _context){
        context = _context;
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseAdapter open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        db.close();
    }

    public  SQLiteDatabase getDatabaseInstance(){
        return db;
    }

    public void insertEntry(String userName,String password, String dateKatataksis, String dateApolusis, String seira, String esso, String enopliDunami, String oplo){
        ContentValues newValues = new ContentValues();
        newValues.put("USERNAME",userName);
        newValues.put("PASSWORD",password);
        newValues.put("DATE_KATATAKSIS",dateKatataksis);
        newValues.put("DATE_APOLUSIS",dateApolusis);
        newValues.put("SEIRA",seira);
        newValues.put("ESSO",esso);
        newValues.put("DUNAMI",enopliDunami);
        newValues.put("OPLO",oplo);
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
            String msg = "User not found";
            return msg;
        }
        cursor.moveToFirst();
        String password= cursor.getString(cursor.getColumnIndex("PASSWORD"));
        cursor.close();
        return password;
    }

    public String getOplo(){
        Cursor cursor = db.query("LOGIN", null, "", new String[]{}, null, null, null);
        if(cursor.getCount()<1)
        {
            cursor.close();
            String msg = "Oplo not found";
            return msg;
        }

        cursor.moveToFirst();
        String oplo= cursor.getString(cursor.getColumnIndex("OPLO"));
        cursor.close();
        return oplo;
    }



    public void updateEntry(String userName, String password, String dateKatataksis, String dateApolusis, String seira, String esso, String enopliDunami, String oplo){

        ContentValues updatedValues = new ContentValues();
        updatedValues.put("USERNAME", userName);
        updatedValues.put("PASSWORD",password);
        updatedValues.put("DATE_KATATAKSIS",dateKatataksis);
        updatedValues.put("DATE_APOLUSIS",dateApolusis);
        updatedValues.put("SEIRA",seira);
        updatedValues.put("ESSO",esso);
        updatedValues.put("DUNAMI",enopliDunami);
        updatedValues.put("OPLO",oplo);

        String where="USERNAME = ?";
        db.update("LOGIN",updatedValues, where, new String[]{userName});
    }


}
