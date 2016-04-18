package com.weiyu.handsomerunner.service;

import com.weiyu.handsomerunner.Config;
import com.weiyu.handsomerunner.network.HttpMethod;
import com.weiyu.handsomerunner.network.NetConnection;

/**
 * Created by Sam on 2016/4/12.
 */
public class LoginService {

    /**
     * a method which can be used to handle the login operations
     * @param userName: the user name
     * @param password: the relevant password which is consistent with user name
     * @param callback: the callback function which can be used to return the results
     */
    public void login(String userName, String password, final LoginCallback callback){

        System.out.println("userName:" + userName + "password:" + password);
        new NetConnection(Config.SERVER_LOGIN, HttpMethod.GET, new NetConnection.ConnectionCallback() {
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
        },userName,password);
    }

    public interface LoginCallback {
        void onSuccess(String result);
        void onFail();
    }

}
