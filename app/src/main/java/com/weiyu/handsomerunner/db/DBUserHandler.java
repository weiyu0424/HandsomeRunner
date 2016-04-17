package com.weiyu.handsomerunner.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.weiyu.handsomerunner.db.DBHelper;
import com.weiyu.handsomerunner.domain.User;
import com.weiyu.handsomerunner.utils.UserUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam on 4/14/2016.
 */
public class DBUserHandler {
    private DBHelper dbHelper = null;
    private Context context = null;
    public DBUserHandler(Context context){
        this.context = context;
    }


    /**
     * insert a user entity to the table
     * @param user: the user entity
     */
    public void insertUser(User user){
        dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserUtils.USER_NAME, user.getUserName());
        contentValues.put(UserUtils.PASSWORD, user.getPassword());
        contentValues.put(UserUtils.AGE, user.getAge());
        contentValues.put(UserUtils.GENDER, user.getGender());
        contentValues.put(UserUtils.LEVEL, user.getLevel());
        contentValues.put(UserUtils.HEIGHT, user.getHeight());
        contentValues.put(UserUtils.WEIGHT, user.getWeight());
        contentValues.put(UserUtils.STEPS, user.getSteps());
        contentValues.put(UserUtils.LATITUDE, user.getLatitude());
        contentValues.put(UserUtils.LONGITUDE, user.getLongitude());
        database.insert(UserUtils.TABLE_NAME, null, contentValues);
        //close the database object
        closeDatabase(database);
    }


    /**
     * update the user table with a user entity
     * @param user: the user entity, which contains the information to be updated
     * @return: if the result is 1, means successful. 0 means fail;
     */
    public boolean updateUser(User user){
        dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserUtils.PASSWORD, user.getPassword());
        contentValues.put(UserUtils.AGE, user.getAge());
        contentValues.put(UserUtils.GENDER, user.getGender());
        contentValues.put(UserUtils.LEVEL, user.getLevel());
        contentValues.put(UserUtils.HEIGHT, user.getHeight());
        contentValues.put(UserUtils.WEIGHT, user.getWeight());
        contentValues.put(UserUtils.STEPS, user.getSteps());
        contentValues.put(UserUtils.UPDATETIME, user.getUpdateTime());
        contentValues.put(UserUtils.LATITUDE, user.getLatitude());
        contentValues.put(UserUtils.LONGITUDE, user.getLongitude());
        database.insert(UserUtils.TABLE_NAME, null, contentValues);
        int result = database.update(UserUtils.TABLE_NAME, contentValues, "userName=?", new String[]{user.getUserName()});

        //close the database object
        closeDatabase(database);
        return  result == 1;
    }


    /**
     * query a column with user name
     * @param userName: user name parameter
     * @return: encapsulate the result to a user entity
     */
    public User queryUser(String userName){
        dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(UserUtils.TABLE_NAME, null, UserUtils.USER_NAME + "=?", new String[]{userName}, null, null, null);
        User user = new User();
        if(cursor.moveToNext()){
            user.setUserName(cursor.getString(cursor.getColumnIndex(UserUtils.USER_NAME)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(UserUtils.PASSWORD)));
            user.setGender(cursor.getString(cursor.getColumnIndex(UserUtils.GENDER)));
            user.setAge(cursor.getInt(cursor.getColumnIndex(UserUtils.AGE)));
            user.setHeight(cursor.getDouble(cursor.getColumnIndex(UserUtils.HEIGHT)));
            user.setWeight(cursor.getDouble(cursor.getColumnIndex(UserUtils.WEIGHT)));
            user.setLevel(cursor.getInt(cursor.getColumnIndex(UserUtils.LEVEL)));
            user.setUpdateTime(cursor.getString(cursor.getColumnIndex(UserUtils.UPDATETIME)));
            user.setLatitude(cursor.getDouble(cursor.getColumnIndex(UserUtils.LATITUDE)));
            user.setLongitude(cursor.getDouble(cursor.getColumnIndex(UserUtils.LONGITUDE)));
            user.setSteps(cursor.getDouble(cursor.getColumnIndex(UserUtils.STEPS)));
        }
        //close the database object
        closeCursor(cursor);
        closeDatabase(database);
        return user;
    }


    /**
     * query the all user columns
     * @return: return the all results with a List collection
     */
    public List<User> queryAllUser(){
        List<User> users = new ArrayList<>();
        dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(UserUtils.TABLE_NAME, null, null, null, null, null, null);

        while(cursor.moveToNext()){
            User user = new User();
            user.setUserName(cursor.getString(cursor.getColumnIndex(UserUtils.USER_NAME)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(UserUtils.PASSWORD)));
            user.setGender(cursor.getString(cursor.getColumnIndex(UserUtils.GENDER)));
            user.setAge(cursor.getInt(cursor.getColumnIndex(UserUtils.AGE)));
            user.setHeight(cursor.getDouble(cursor.getColumnIndex(UserUtils.HEIGHT)));
            user.setWeight(cursor.getDouble(cursor.getColumnIndex(UserUtils.WEIGHT)));
            user.setLevel(cursor.getInt(cursor.getColumnIndex(UserUtils.LEVEL)));
            user.setUpdateTime(cursor.getString(cursor.getColumnIndex(UserUtils.UPDATETIME)));
            user.setLatitude(cursor.getDouble(cursor.getColumnIndex(UserUtils.LATITUDE)));
            user.setLongitude(cursor.getDouble(cursor.getColumnIndex(UserUtils.LONGITUDE)));
            user.setSteps(cursor.getDouble(cursor.getColumnIndex(UserUtils.STEPS)));
            users.add(user);
        }

        //close the database object
        closeCursor(cursor);
        closeDatabase(database);

        return users;
    }


    /**
     * close the current cursor
     * @param cursor: the cursor
     */
    public void closeCursor(Cursor cursor){
        if(cursor != null){
            cursor.close();
            cursor = null;
        }
    }

    /**
     * close the current database
     * @param database:the database
     */
    public void closeDatabase(SQLiteDatabase database){
        if(database != null){
            database.close();
            database = null;
        }
    }
}
