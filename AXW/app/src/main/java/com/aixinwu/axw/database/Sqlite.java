package com.aixinwu.axw.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by zhangyan on 2015/10/29.
 */
public class Sqlite extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "AXWuser.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + "AXWuser" + " (" +
                    "userId" + " INTEGER PRIMARY KEY AUTOINCREMENT" +
                    COMMA_SEP +
                    "phoneNumber" + TEXT_TYPE +
                    COMMA_SEP +
                    "pwd" + TEXT_TYPE +
                    " )";


    //private static final String SQL_CREATE_ENTRIES = "CREATE TABLE cart (id varchar(255), name varchar(255), price integer)";

    public Sqlite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);


        Log.d("debug", "create table!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
