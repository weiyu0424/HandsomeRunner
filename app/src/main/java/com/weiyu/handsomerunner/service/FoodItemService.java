package com.weiyu.handsomerunner.service;

import android.text.TextUtils;

import com.weiyu.handsomerunner.Config;
import com.weiyu.handsomerunner.domain.Food;
import com.weiyu.handsomerunner.network.HttpMethod;
import com.weiyu.handsomerunner.network.NetConnection;
import com.weiyu.handsomerunner.utils.FoodUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam on 4/17/2016.
 */
public class FoodItemService {

    /**
     * create a method to retrieve food items with a given category
     * @param category:the given category
     * @param callback: a callback interface which used to deliver the results
     */
    public void retrieveFoodItems(String category, final FoodItemCallback callback){
        new NetConnection(Config.QUERY_FOOD_BY＿CATEGORY, HttpMethod.GET, new NetConnection.ConnectionCallback() {
            @Override
            public void onSuccess(String result) {
                if(callback != null){
                    List<Food> foodItems = parseFoodItems(result);
                    callback.onSuccess(foodItems);
                }
            }

            @Override
            public void onFail() {
                if(callback != null){
                    callback.onFail();
                }
            }
        },category);
    }


    /**
     * parse the result from the server into a List collection
     * @param result: the result from the server
     * @return: the list collection
     */
    private List<Food> parseFoodItems(String result){
        List<Food> foods = new ArrayList<>();
        try {
//            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = new JSONArray(result);
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Food food = new Food();
                food.setFoodId(jsonObject.getString(FoodUtils.FOOD_ID));
                food.setFoodName(jsonObject.getString(FoodUtils.FOOD_NAME));
                food.setServing(jsonObject.getDouble(FoodUtils.SERVING));
                food.setFat(jsonObject.getDouble(FoodUtils.FAT));
                food.setCalories(jsonObject.getDouble(FoodUtils.CALORIES));
                food.setUnit(jsonObject.getString(FoodUtils.UNIT));
                foods.add(food);
            }
            return foods;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * add daily diet
     * @param userName
     * @param foodId
     * @param counts
     * @param updateTime
     * @param callback
     */
    public void addFoodItems(String userName, String foodId, String counts, String updateTime, final FoodItemCallback callback){
        updateTime = updateTime.replace(" ","%20");
        new NetConnection(Config.ADD_FOOD_ITEMS, HttpMethod.GET, new NetConnection.ConnectionCallback() {
            @Override
            public void onSuccess(String result) {
                if(!TextUtils.isEmpty(result)){
                    if(callback != null){
                        callback.onSuccess(result);
                    }
                }else{
                    if(callback != null){
                        callback.onFail();
                    }
                }
            }

            @Override
            public void onFail() {
                if(callback != null){
                    callback.onFail();
                }
            }
        },userName,foodId,counts,updateTime);
    }

    /**
     * a customized callback which can be userd to pass result;
     */
    public interface FoodItemCallback{
        void onSuccess(Object result);
        void onFail();
    }
}
