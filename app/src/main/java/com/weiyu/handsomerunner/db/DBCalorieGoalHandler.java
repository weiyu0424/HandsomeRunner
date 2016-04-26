package com.weiyu.handsomerunner.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.weiyu.handsomerunner.Config;
import com.weiyu.handsomerunner.utils.CalorieGoalUtils;

/**
 * Created by Sam on 4/21/2016.
 */
public class DBCalorieGoalHandler {
    private Context context = null;
    private String userName = null;

    public DBCalorieGoalHandler(Context context){
        this.context = context;
        userName = Config.getUserName(context);
    }


    public double getCalorie(){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + CalorieGoalUtils.TABLE_NAME + " where updateTime=? and userName=?", new String[]{Config.today(),userName});
        if(cursor.moveToNext()){
            double calorie = cursor.getDouble(cursor.getColumnIndex(CalorieGoalUtils.CALORIE));
            return calorie;
        }
        return 0;
    }


    /**
     * insert a record into database
     * @param calorie: the calorie a user set
     */
    public void insertCalorie(double calorie){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CalorieGoalUtils.CALORIE,calorie);
        contentValues.put(CalorieGoalUtils.UPDATETIME, Config.today());
        contentValues.put(CalorieGoalUtils.USERNAME, userName);
        db.insert(CalorieGoalUtils.TABLE_NAME,null,contentValues);
    }


    /**
     * update the calorie record of today
     * @param calorie: the calorie a user want to update
     */
    public void updateCalorie(double calorie){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CalorieGoalUtils.CALORIE,calorie);
//        contentValues.put(CalorieGoalUtils.UPDATETIME, Config.today());
        db.update(CalorieGoalUtils.TABLE_NAME,contentValues,"updateTime=? and userName=?",new String[]{Config.today(),userName});
       // db.insert(CalorieGoalUtils.TABLE_NAME,null,contentValues);
    }


}
