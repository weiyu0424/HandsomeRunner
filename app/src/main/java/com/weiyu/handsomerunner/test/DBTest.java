package com.weiyu.handsomerunner.test;

import android.test.AndroidTestCase;

import com.weiyu.handsomerunner.db.DBUserHandler;
import com.weiyu.handsomerunner.domain.User;

/**
 * Created by Sam on 4/14/2016.
 */
public class DBTest extends AndroidTestCase {



    public void userTest(){
        DBUserHandler handler = new DBUserHandler(getContext());
        User tom = handler.queryUser("Tom");
        System.out.println(tom);
    }
}
