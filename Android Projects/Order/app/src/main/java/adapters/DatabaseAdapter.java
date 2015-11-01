package adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import databasehandlers.DataBaseHelper;
import functions.AppConstant;


public class DatabaseAdapter {
    public SQLiteDatabase db;
    private final Context context;
    private DataBaseHelper dbHelper;
    private ContentValues newValues, updatedValues;
    private String where, password;
    private int numberOFEntriesDeleted;
    private Cursor cursor;

    public DatabaseAdapter(Context _context){
        context = _context;
        dbHelper = new DataBaseHelper(context, AppConstant.DATABASE_NAME, null, AppConstant.DATABASE_VERSION);
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

    public void insertEntry(String userName,String password){
        newValues = new ContentValues();
        newValues.put("USERNAME",userName);
        newValues.put("PASSWORD",password);
        db.insert("LOGIN", null, newValues);
    }
    public int deleteEntry(String UserName){
        where="USERNAME=?";
        numberOFEntriesDeleted= db.delete("LOGIN", where, new String[]{UserName}) ;
        return numberOFEntriesDeleted;
    }

    public String getSinlgeEntry(String userName){
        cursor=db.query("LOGIN", null, " USERNAME=?", new String[]{userName}, null, null, null);
        if(cursor.getCount()<1)
        {
            cursor.close();
            String msg = "User not found";
            return msg;
        }
        cursor.moveToFirst();
        password= cursor.getString(cursor.getColumnIndex("PASSWORD"));
        cursor.close();
        return password;
    }

    public void updateEntry(String userName,String password){
        updatedValues = new ContentValues();
        updatedValues.put("USERNAME", userName);
        updatedValues.put("PASSWORD",password);
        where="USERNAME = ?";
        db.update("LOGIN",updatedValues, where, new String[]{userName});
    }
}
