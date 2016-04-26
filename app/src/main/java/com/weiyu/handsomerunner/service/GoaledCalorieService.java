package com.weiyu.handsomerunner.service;

import com.weiyu.handsomerunner.Config;
import com.weiyu.handsomerunner.network.HttpMethod;
import com.weiyu.handsomerunner.network.NetConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sam on 4/22/2016.
 */
public class GoaledCalorieService {

    /**
     * a method which can be used to retrieve goaled calorie from server
     * @param userName: the given user name
     * @param updateTime: the given day
     */
    public void getGoaledCalorie(String userName, String updateTime, final GoaledCalorieCallback callback){
        new NetConnection(Config.GET_GOALED_CALORIE, HttpMethod.GET, new NetConnection.ConnectionCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    if(jsonArray.length() != 0){
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String goaledCalories = jsonObject.getString("goaledCalories");
                        if(callback != null){
                            callback.onSuccess(goaledCalories);
                        }
                    }else{
                        if(callback != null){
                            callback.onEmpty();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if(callback != null){
                        callback.onFail();
                    }
                }

                //System.out.println("result:" + result);
            }

            @Override
            public void onFail() {
                if(callback != null){
                    callback.onFail();
                }
            }
        },userName,updateTime);
    }


    /**
     * insert a calorie record into server
     * @param userName: the given name
     * @param updateTime: a given time
     * @param goaledCalories: the goaledCalories which need to be inserted into server
     * @param callback: a callback interface to deliver the result
     */
    public void insertGoaledCalories(String userName, String updateTime, String goaledCalories, final GoaledCalorieCallback callback){
        new NetConnection(Config.INSERT_GOALED_CALORIE, HttpMethod.GET, new NetConnection.ConnectionCallback() {
            @Override
            public void onSuccess(String result) {
                if(callback != null){
                    callback.onSuccess(result);
                }
            }

            @Override
            public void onFail() {
                if(callback != null){
                    callback.onFail();
                }
            }
        },userName, updateTime, goaledCalories);
    }


    /**
     * update the goaled calorie in the server
     * @param userName
     * @param updateTime
     * @param goaledCalories
     * @param callback
     */
    public void updateGoaledCalories(String userName, String updateTime, String goaledCalories, final GoaledCalorieCallback callback){
        new NetConnection(Config.UPDATE_GOALED_CALORIE, HttpMethod.GET, new NetConnection.ConnectionCallback() {
            @Override
            public void onSuccess(String result) {
                if(callback != null){
                    callback.onSuccess(result);
                }
            }

            @Override
            public void onFail() {
                if(callback != null){
                    callback.onFail();
                }
            }
        },userName, updateTime, goaledCalories);
    }

    /**
     * get consumed and burned calories with a given user name and time
     * @param userName
     * @param updateTime
     * @param callback
     */
    public void getConsumedAndBurnedCalories(String userName, String updateTime,final GoaledCalorieCallback callback){
        new NetConnection(Config.GET_CONSUMED_AND_BURNED_CALORIES, HttpMethod.GET, new NetConnection.ConnectionCallback() {
            @Override
            public void onSuccess(String result) {
                if(callback != null){
                    callback.onSuccess(result);
                }
            }

            @Override
            public void onFail() {
                if(callback != null){
                    callback.onFail();
                }
            }
        },userName, updateTime);
    }


    /**
     * a callback interface which can be utilized to deliver data
     */
    public interface GoaledCalorieCallback{
        void onSuccess(Object obj);
        void onFail();
        void onEmpty();
    }
}
