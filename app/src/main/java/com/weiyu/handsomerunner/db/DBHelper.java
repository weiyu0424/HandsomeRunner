package com.weiyu.handsomerunner.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * created by Sam on 4/14/2016
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "id27315932";
    public static final String CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS user(" +
            "_id integer primary key autoincrement," +
            "userName varchar(50) not null," +
            "password varchar(50) not null," +
            "age integer not null," +
            "height double not null," +
            "weight double not null," +
            "gender varchar(50) not null," +
            "level integer not null," +
            "steps double null default 0," +
            "updateTime datetime default CURRENT_TIMESTAMP not null," +
            "latitude double null default 190," +
            "longitude double null default 180" +
            ");";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create a user table
        db.execSQL(CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
