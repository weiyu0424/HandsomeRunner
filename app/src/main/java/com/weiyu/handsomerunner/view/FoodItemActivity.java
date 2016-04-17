package com.weiyu.handsomerunner.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.weiyu.handsomerunner.Config;
import com.weiyu.handsomerunner.R;
import com.weiyu.handsomerunner.network.NetConnection;
import com.weiyu.handsomerunner.network.NetworkUtils;
import com.weiyu.handsomerunner.serice.FoodItemService;

public class FoodItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //receive the intent from HomeFragment
        Intent intent = getIntent();
        if(intent != null){
            String category = intent.getStringExtra(Config.CATEGORY);
            if(NetworkUtils.isNetworkAvailable(this)) {
                getFoodItemsWithCategory(category);
            }else{
                Config.toast(this,"Oops, the network doesn't work, please enable the mobile data or connect to the WIFI");
            }
        }
    }

    /**
     * create a method to retrieve the food items from the server with a certain category
     * @param category: the specific category
     */
    private void getFoodItemsWithCategory(String category) {
        FoodItemService service = new FoodItemService();
        service.retrieveFoodItems(category, new FoodItemService.FoodItemCallback() {
            @Override
            public void onSuccess(String result) {
                System.out.println("result:" + result);
            }

            @Override
            public void onFail() {

            }
        });
    }


}
