package com.weiyu.handsomerunner.service;

import com.weiyu.handsomerunner.Config;
import com.weiyu.handsomerunner.network.HttpMethod;
import com.weiyu.handsomerunner.network.NetConnection;

/**
 * Created by Sam on 4/24/2016.
 */
public class StepsService {


    /**
     * insert total steps of a given day into server
     * @param userName: the user name
     * @param updateTime: tht current day
     * @param steps: total steps
     * @param callback: a callback interface which can be delivered results
     */
    public void insertSteps(String userName, String updateTime, String steps, final StepsServiceCallback callback){
        new NetConnection(Config.ADD_STEPS, HttpMethod.GET, new NetConnection.ConnectionCallback() {
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
        },userName,updateTime,steps);
    }


    public interface StepsServiceCallback{
        void onSuccess(Object obj);
        void onFail();
    }
}
