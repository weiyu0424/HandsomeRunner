package com.weiyu.handsomerunner;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Sam on 2016/4/12.
 */
public class Config {
    public static final String KEY_TOKEN = "token";
    public static final String APP_ID = "com.weiyu.handsomerunner";
    public static final String CHARSET = "utf-8";

    //register url
    public static final String SERVER_REGISTER = "http://172.16.120.11:8080/CaloriesServer/webresources/user/register";

    //login url
    public static final String SERVER_LOGIN = "http://172.16.120.11:8080/CaloriesServer/webresources/user/login";
    //get goaled calorie

    public static final String GET_GOALED_CALORIE = "http://172.16.120.11:8080/CaloriesServer/webresources/report/getGoaledCalories";

    //insert a goaled calorie record
    public static final String INSERT_GOALED_CALORIE = "http://172.16.120.11:8080/CaloriesServer/webresources/report/addGoaledCalorie";

    public static final String UPDATE_GOALED_CALORIE = "http://172.16.120.11:8080/CaloriesServer/webresources/report/updateGoaledCalorie";


    public static final String GET_CONSUMED_AND_BURNED_CALORIES = "http://172.16.120.11:8080/CaloriesServer/webresources/consumerecord/calculateBurnedAndConsumedCaloriesByUserNameAndTime";


    public static final String ADD_STEPS = "http://172.16.120.11:8080/CaloriesServer/webresources/report/addSteps";

    /**
     * query food by a category, in this app. I adopt a vague recognition
     */
    public static final String QUERY_FOOD_BY＿CATEGORY = "http://172.16.120.11:8080/CaloriesServer/webresources/food/findByCategory";
    public static final String USER_NAME = "userName";
    public static final String PASSWORD = "password";
    public static final String TOTAL_STEPS = "totalSteps";


    public static final String CATEGORY = "category";

    public static final String CONSUME_KEY = "99743ef85c6b4637bbd6e3db15435906";


    public static final String SHARED_SECRET = "e0e9fffc9682406e8be6f9e96801215e";




    /**
     * to judge that the user is login or not
     * @param context: the current environment
     * @return: true represent that the user has logined, false represent that the user has not logined
     */
    public static boolean isLogin(Context context){
        return context.getSharedPreferences(APP_ID,Context.MODE_PRIVATE).getBoolean(KEY_TOKEN,false);
    }

    /**
     * obtain the user name from SharedPreference
     * @param context: the current context
     * @return: the user name
     */
    public static String getUserName(Context context){
        return context.getSharedPreferences(APP_ID,Context.MODE_PRIVATE).getString(USER_NAME,"");
    }


    /**
     * set the login state true
     * @param context: the current environment
     * @param userName: the userName which need to be cached
     */
    public static void setLogin(Context context,String userName){
        SharedPreferences.Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putBoolean(KEY_TOKEN,true);
        editor.putString(USER_NAME,userName);
        editor.commit();
    }


    /**
     * cache the current total steps into shared preferences
     * @param context: the current environment
     * @param totalSteps: total steps
     */
    public static void setTotalSteps(Context context, int totalSteps){
        SharedPreferences.Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putInt(TOTAL_STEPS,totalSteps);
        editor.commit();
    }


    /**
     * get cached total steps
     * @param context
     * @return: return the cached total steps
     */
    public static int getTotalSteps(Context context){
        return context.getSharedPreferences(APP_ID,Context.MODE_PRIVATE).getInt(TOTAL_STEPS,0);
    }


    /**
     * to display the toast information
     * @param context: the current environment
     * @param content: the content which can be used to display
     */
    public static void toast(Context context,String content){
        Toast.makeText(context,content,Toast.LENGTH_SHORT).show();
    }


    /**
     * obtain the current time
     * @return: return the current time
     */
    public static String currentTime(){
        long timeMillis = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date(timeMillis);
        String currentTime = format.format(date);
        return currentTime;
    }


    /**
     * obtain the current time
     * @return
     */
    public static String today(){
        long timeMillis = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(timeMillis);
        String currentTime = format.format(date);
        return currentTime;
    }
}
