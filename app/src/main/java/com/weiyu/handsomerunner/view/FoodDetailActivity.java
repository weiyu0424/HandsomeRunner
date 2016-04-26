package com.weiyu.handsomerunner.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.weiyu.handsomerunner.R;
import com.weiyu.handsomerunner.adapter.FoodDetailAdapter;
import com.weiyu.handsomerunner.domain.Bing;
import com.weiyu.handsomerunner.domain.Food;
import com.weiyu.handsomerunner.domain.FoodItem;
import com.weiyu.handsomerunner.network.SearchMethod;
import com.weiyu.handsomerunner.service.FatSecretAPIService;
import com.weiyu.handsomerunner.service.SearchService;

import java.util.List;

public class FoodDetailActivity extends AppCompatActivity {
    private ImageView ivFoodImage = null;
    private TextView tvFoodName = null;
    private TextView tvFoodParameters = null;
    private TextView tvfoodDescription = null;
    private Food food = null;
    private ListView lvFoodDetail = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        food = bundle.getParcelable("foodItem");
        System.out.println("food:" + food);
        initView();
        initData();
    }

    private void initView() {
        ivFoodImage = (ImageView) findViewById(R.id.iv_food_image);
        tvFoodName = (TextView) findViewById(R.id.tv_foodName);
        tvFoodParameters = (TextView) findViewById(R.id.tv_food_parameters);
        tvfoodDescription = (TextView) findViewById(R.id.tv_food_description);
        lvFoodDetail = (ListView) findViewById(R.id.lv_food_detail);
    }

    private void initData() {
        String description = food.getServing() + food.getUnit() + " | " + food.getFat() + "g fat | " + food.getCalories() + "calories";
        tvFoodName.setText(food.getFoodName());
        tvFoodParameters.setText(description);

        //retrieved data from FatSecretAPI
        new FatSecretAPIService().getFoodItems(food.getFoodName(), 5, new FatSecretAPIService.FatSecretServiceCallback() {
            @Override
            public void onSuccess(Object obj) {
                List<FoodItem> foodItems = (List<FoodItem>) obj;
                FoodDetailAdapter adapter = new FoodDetailAdapter(FoodDetailActivity.this,foodItems);
                lvFoodDetail.setAdapter(adapter);
            }

            @Override
            public void onFail() {

            }
        });


        //retrieved description and images from bing
        new SearchService().search(food.getFoodName(), 1, SearchMethod.WEB, new SearchService.SearchSearviceCallback() {
            @Override
            public void onSuccess(Object obj) {
                Bing bing = (Bing) obj;
                tvfoodDescription.setText(bing.getDescription());
            }

            @Override
            public void onFail() {

            }
        });

        new SearchService().search(food.getFoodName(), 1, SearchMethod.IMAGE, new SearchService.SearchSearviceCallback() {
            @Override
            public void onSuccess(Object obj) {
                Bing bing = (Bing) obj;
                Picasso.with(FoodDetailActivity.this).load(bing.getMediaUrl()).into(ivFoodImage);
            }

            @Override
            public void onFail() {

            }
        });
    }



}
