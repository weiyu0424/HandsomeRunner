package com.weiyu.handsomerunner.test;

import android.test.AndroidTestCase;

import com.weiyu.handsomerunner.Config;
import com.weiyu.handsomerunner.db.DBUserHandler;
import com.weiyu.handsomerunner.domain.User;
import com.weiyu.handsomerunner.platform.FatSecretAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by Sam on 4/14/2016.
 */
public class DBTest {

    public static void main(String[] args) {
        FatSecretAPI api = new FatSecretAPI(Config.CONSUME_KEY, Config.SHARED_SECRET);
//        String result = api.getFoodItems("cake(with icing)");
    }
}
