package com.weiyu.handsomerunner.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.weiyu.handsomerunner.Config;
import com.weiyu.handsomerunner.domain.Steps;
import com.weiyu.handsomerunner.utils.StepsUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam on 4/24/2016.
 */
public class DBStepsHandler {
    private Context context = null;
    private String userName = null;


    public DBStepsHandler(Context context){
        this.context = context;
        userName = Config.getUserName(context);
    }


    /**
     * insert a steps record into steps table
     * @param steps
     */
    public void insertSteps(int steps){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(StepsUtils.USERNAME, userName);
        contentValues.put(StepsUtils.STEPS,steps);
        contentValues.put(StepsUtils.UPDATETIME,Config.getConcreteTime());
        db.insert(StepsUtils.TABLE_NAME,null,contentValues);
    }

    /**
     * query all the steps record from the database
     * @return
     */
    public List<Steps> querySteps(){
        List<Steps> steps = new ArrayList<>();
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(StepsUtils.TABLE_NAME, null, null, null, null, null, null);
        while(cursor.moveToNext()){
            Steps step = new Steps();
            step.setUserName(cursor.getString(cursor.getColumnIndex(StepsUtils.USERNAME)));
            step.setSteps(cursor.getInt(cursor.getColumnIndex(StepsUtils.STEPS)));
            step.setUpdateTime(cursor.getString(cursor.getColumnIndex(StepsUtils.UPDATETIME)));
            steps.add(step);
        }
        return steps;
    }


    /**
     * query all the steps record from the database
     * @return
     */
    public List<Steps> queryStepsByTime(String updateTime){
        List<Steps> steps = new ArrayList<>();
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //strftime('%Y-%m-%d',t1) = '2012-08-01'
        Cursor cursor = db.rawQuery("select * from " + StepsUtils.TABLE_NAME + " where strftime('%Y-%m-%d',updateTime)=?",new String[]{updateTime});
//        Cursor cursor = db.query(StepsUtils.TABLE_NAME, null, "updateTime=?", new String[]{updateTime}, null, null, null);
        while(cursor.moveToNext()){
            Steps step = new Steps();
            step.setUserName(cursor.getString(cursor.getColumnIndex(StepsUtils.USERNAME)));
            step.setSteps(cursor.getInt(cursor.getColumnIndex(StepsUtils.STEPS)));
            step.setUpdateTime(cursor.getString(cursor.getColumnIndex(StepsUtils.UPDATETIME)));
            steps.add(step);
        }
        return steps;
    }

}
