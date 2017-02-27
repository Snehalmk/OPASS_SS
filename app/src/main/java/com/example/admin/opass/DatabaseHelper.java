package com.example.admin.opass;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.example.admin.opass.UserContract.NewUserInfo.TABLE_NAME;
import static com.example.admin.opass.UserContract.NewUserInfo.USER_EMAIL;
import static com.example.admin.opass.UserContract.NewUserInfo.USER_NAME;
import static com.example.admin.opass.UserContract.NewUserInfo.USER_PASS;

/**
 * Created by dell on 16/02/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="OPASS.db";
    private static final int DATABASE_VERSION=1;
    private static final String CREATE_QUERY="CREATE TABLE "+TABLE_NAME+"("+USER_NAME+" TEXT,"+USER_EMAIL+" TEXT,"+USER_PASS+" TEXT);";
    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME , null , DATABASE_VERSION);
        Log.e("Database operations","Database created/opened...");
    }


    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_QUERY);
        Log.e("Database operations","Table Created");
    }
    public void addInfo(String uname,String uemail,String upassword,SQLiteDatabase db)
    {
        ContentValues ContentValues = new ContentValues();
        ContentValues.put(USER_NAME,uname);
        ContentValues.put(USER_EMAIL,uemail);
        ContentValues.put(USER_PASS,upassword);
        db.insert(TABLE_NAME,null,ContentValues);
        Log.e("Database operations","One row inserted...");
    }
    public Cursor getInfo(SQLiteDatabase db)
    {
        Cursor cursor;
        String[] projections={USER_NAME,USER_EMAIL,USER_PASS};
        cursor=db.query(TABLE_NAME,projections,null,null,null,null,null);
        return cursor;
    }
    public Cursor getName(SQLiteDatabase db)
    {
        Cursor cursor;
        String[] projections={USER_NAME};
        cursor=db.query(TABLE_NAME,projections,null,null,null,null,null);
        return cursor;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
