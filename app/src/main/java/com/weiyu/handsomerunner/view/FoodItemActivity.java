package com.weiyu.handsomerunner.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.weiyu.handsomerunner.Config;
import com.weiyu.handsomerunner.R;
import com.weiyu.handsomerunner.adapter.FoodItemAdapter;
import com.weiyu.handsomerunner.domain.Food;
import com.weiyu.handsomerunner.network.NetworkUtils;
import com.weiyu.handsomerunner.service.FoodItemService;

import java.util.List;

public class FoodItemActivity extends AppCompatActivity {
    private ListView lvFoodItems = null;
    private LinearLayout llFoodItems = null;
    private ImageView ivLoadOfFoodItems = null;
    private TextView tvLoadOfFoodItems = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();

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

    private void initView() {
        lvFoodItems = (ListView) findViewById(R.id.lv_food_items);
        ivLoadOfFoodItems = (ImageView) findViewById(R.id.iv_load_of_food_items);
        tvLoadOfFoodItems = (TextView) findViewById(R.id.tv_load_of_food_items);
        llFoodItems = (LinearLayout) findViewById(R.id.ll_food_items);
        ivLoadOfFoodItems = (ImageView) findViewById(R.id.iv_load_of_food_items);
        tvLoadOfFoodItems = (TextView) findViewById(R.id.tv_load_of_food_items);
    }

    /**
     * create a method to retrieve the food items from the server with a certain category
     * @param category: the specific category
     */
    private void getFoodItemsWithCategory(String category) {
        FoodItemService service = new FoodItemService();
        service.retrieveFoodItems(category, new FoodItemService.FoodItemCallback() {
            @Override
            public void onSuccess(Object result) {
                List<Food> foodItems = (List<Food>) result;

                //if load food items successfully, make Hint view invisible
                llFoodItems.setVisibility(View.GONE);
                if(result != null) {
                    for(Food food : foodItems){
                        System.out.println(food);
                    }
                }

                FoodItemAdapter adapter = new FoodItemAdapter(FoodItemActivity.this, foodItems);
                lvFoodItems.setAdapter(adapter);
            }

            @Override
            public void onFail() {
                Config.toast(FoodItemActivity.this,"Oops, obtaining food items from the server failed!");
            }
        });
    }


}
