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


    /**
     * query food by a category, in this app. I adopt a vague recognition
     */
    public static final String QUERY_FOOD_BY＿CATEGORY = "http://172.16.120.11:8080/CaloriesServer/webresources/food/findFoodByCategory";
    public static final String USER_NAME = "userName";
    public static final String PASSWORD = "password";


    public static final String CATEGORY = "category";


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
}
