package com.weiyu.handsomerunner.service;

import com.weiyu.handsomerunner.Config;
import com.weiyu.handsomerunner.domain.FoodItem;
import com.weiyu.handsomerunner.platform.FatSecretAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam on 4/19/2016.
 */
public class FatSecretAPIService {


    /**
     * get food items with a given category from FatSecret APIS
     * @param content: the current environment
     * @param max_results: the number of results
     * @param callback: a callback interface, which can deliver results to its caller
     */
    public void getFoodItems(String content, int max_results, final FatSecretServiceCallback callback){
        FatSecretAPI api = new FatSecretAPI(Config.CONSUME_KEY,Config.SHARED_SECRET);
        api.getFoodItems(content, max_results, new FatSecretAPI.FatSecretCallback() {
            @Override
            public void onSuccess(Object obj) {
                String content = (String) obj;
                try {
                    JSONObject jsonObject = new JSONObject(content);
                    JSONArray jsonArray = jsonObject.getJSONObject("foods").getJSONArray("food");

                    List<FoodItem> foodItems = new ArrayList<FoodItem>();
                    for(int i = 0;i < jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        FoodItem foodItem = new FoodItem();
                        foodItem.setFoodId(object.getString("food_id"));
                        foodItem.setFoodName(object.getString("food_name"));
                        foodItem.setFoodDescription(object.getString("food_description"));
                        foodItem.setFoodType(object.getString("food_type"));
                        foodItem.setFoodUrl(object.getString("food_url"));
                        foodItems.add(foodItem);
                    }

                    if(callback != null){
                        callback.onSuccess(foodItems);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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
        });

    }

    public interface FatSecretServiceCallback{
        void onSuccess(Object obj);
        void onFail();
    }
}
