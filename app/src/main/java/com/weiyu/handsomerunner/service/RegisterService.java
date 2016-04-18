package com.weiyu.handsomerunner.service;

import com.weiyu.handsomerunner.Config;
import com.weiyu.handsomerunner.domain.User;
import com.weiyu.handsomerunner.network.HttpMethod;
import com.weiyu.handsomerunner.network.NetConnection;

/**
 * Created by Sam on 4/13/2016.
 */
public class RegisterService {

    /**
     * register an account for a user
     * @param user: user information
     * @param callback: the callback function, the result information can be delivered by this callback
     */
    public void register(User user, final RegisterCallback callback) {
        new NetConnection(Config.SERVER_REGISTER, HttpMethod.GET, new NetConnection.ConnectionCallback() {
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
        }, user.getUserName(), user.getPassword(), user.getAge() + "",
                user.getGender(), user.getHeight() + "", user.getWeight() + "", user.getLevel() + "");
    }


    /**
     * a callback interface, which can be used to pass the result to the caller.
     */
    public interface RegisterCallback {
        void onSuccess(String result);

        void onFail();
    }
}
