package com.weiyu.handsomerunner.serice;

import com.weiyu.handsomerunner.Config;
import com.weiyu.handsomerunner.network.HttpMethod;
import com.weiyu.handsomerunner.network.NetConnection;

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
                    callback.onSuccess(result);
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
     * a customized callback which can be userd to pass result;
     */
    public interface FoodItemCallback{
        void onSuccess(String result);
        void onFail();
    }
}
