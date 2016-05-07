package com.weiyu.handsomerunner.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.weiyu.handsomerunner.Config;
import com.weiyu.handsomerunner.domain.DailyDiet;
import com.weiyu.handsomerunner.domain.Steps;
import com.weiyu.handsomerunner.utils.DailyDietUtils;
import com.weiyu.handsomerunner.utils.StepsUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam on 5/3/2016.
 */
public class DBDailyDietHandler {
    private Context context = null;
    private String userName = null;


    public DBDailyDietHandler(Context context){
        this.context = context;
        userName = Config.getUserName(context);
    }


    /**
     * insert a daily diet entity to database
     * @param dailyDiet: the daily diet entity
     */
    public void insertDailyDiet(DailyDiet dailyDiet){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DailyDietUtils.FOOD_ID,dailyDiet.getFoodId());
        contentValues.put(DailyDietUtils.FOOD_NAME,dailyDiet.getFoodName());
        contentValues.put(DailyDietUtils.USERNAME,userName);
        contentValues.put(DailyDietUtils.FOOD_IMAGE_URL,dailyDiet.getFoodImageUrl());
        contentValues.put(DailyDietUtils.COUNTS,dailyDiet.getCount());
        contentValues.put(DailyDietUtils.UPDATETIME,Config.getConcreteTime());
        db.insert(DailyDietUtils.TABLE_NAME,null,contentValues);
        db.close();
    }



    /**
     * query all the daily diet records from the database
     * @return
     */
    public List<DailyDiet> queryDailyDietByTime(String updateTime){
        List<DailyDiet> dailyDiets = new ArrayList<>();
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //strftime('%Y-%m-%d',t1) = '2012-08-01'
        Cursor cursor = db.rawQuery("select * from " + DailyDietUtils.TABLE_NAME + " where strftime('%Y-%m-%d',updateTime)=?",new String[]{updateTime});
        while(cursor.moveToNext()){
            DailyDiet dailyDiet = new DailyDiet();
            dailyDiet.setFoodId(cursor.getString(cursor.getColumnIndex(DailyDietUtils.FOOD_ID)));
            dailyDiet.setFoodName(cursor.getString(cursor.getColumnIndex(DailyDietUtils.FOOD_NAME)));
            dailyDiet.setUserName(cursor.getString(cursor.getColumnIndex(DailyDietUtils.USERNAME)));
            dailyDiet.setCount(cursor.getInt(cursor.getColumnIndex(DailyDietUtils.COUNTS)));
            dailyDiet.setUpdateTime(cursor.getString(cursor.getColumnIndex(DailyDietUtils.UPDATETIME)));
            dailyDiet.setFoodImageUrl(cursor.getString(cursor.getColumnIndex(DailyDietUtils.FOOD_IMAGE_URL)));
            dailyDiets.add(dailyDiet);
        }
        return dailyDiets;
    }

}
