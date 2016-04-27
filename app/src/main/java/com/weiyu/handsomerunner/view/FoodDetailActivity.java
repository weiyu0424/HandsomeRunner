package com.weiyu.handsomerunner.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.weiyu.handsomerunner.Config;
import com.weiyu.handsomerunner.R;
import com.weiyu.handsomerunner.adapter.FoodDetailAdapter;
import com.weiyu.handsomerunner.domain.Bing;
import com.weiyu.handsomerunner.domain.Food;
import com.weiyu.handsomerunner.domain.FoodItem;
import com.weiyu.handsomerunner.network.SearchMethod;
import com.weiyu.handsomerunner.service.FatSecretAPIService;
import com.weiyu.handsomerunner.service.FoodItemService;
import com.weiyu.handsomerunner.service.SearchService;

import java.util.List;

public class FoodDetailActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView ivFoodImage = null;
    private TextView tvFoodName = null;
    private TextView tvFoodParameters = null;
    private TextView tvfoodDescription = null;
    private Food food = null;
    private ListView lvFoodDetail = null;
    private EditText etFoodCounts = null;
    private TextView tvFoodItemsAdd = null;
    private ImageView ivBackOfFoodAdd = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle("Food Detail");
//        setSupportActionBar(toolbar);
//        Bitmap bitmap = BitmapFactory.decodeStream(conn.getInputStream());
//        iamgeView.setImageBitmap(bitmap);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        food = bundle.getParcelable("foodItem");
        System.out.println("food:" + food);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        ivFoodImage = (ImageView) findViewById(R.id.iv_food_image);
        tvFoodName = (TextView) findViewById(R.id.tv_foodName);
        tvFoodParameters = (TextView) findViewById(R.id.tv_food_parameters);
        tvfoodDescription = (TextView) findViewById(R.id.tv_food_description);
        lvFoodDetail = (ListView) findViewById(R.id.lv_food_detail);
        ivBackOfFoodAdd = (ImageView) findViewById(R.id.iv_back_of_food_add);
        etFoodCounts = (EditText) findViewById(R.id.et_food_counts);
        tvFoodItemsAdd = (TextView) findViewById(R.id.tv_food_items_add);
    }


    /**
     * get food description and relevant images from bing search engine
     * get more details from fatSecretAPI
     */
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

    private void initEvent() {
        tvFoodItemsAdd.setOnClickListener(this);
        ivBackOfFoodAdd.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back_of_food_add:
                finish();
                break;
            case R.id.tv_food_items_add:
                addDailyDiet();
                break;
        }
    }


    /**
     * add the given food to user's daily diet
     */
    private void addDailyDiet() {
        final String foodNums = etFoodCounts.getText().toString().trim();
        if(!TextUtils.isEmpty(foodNums)){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Do you really want to add " + food.getFoodName() + " to your daily diet?");
            builder.setPositiveButton("AGREE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    new FoodItemService().addFoodItems(Config.getUserName(FoodDetailActivity.this), food.getFoodId(), foodNums, Config.getConcreteTime(), new FoodItemService.FoodItemCallback() {
                        @Override
                        public void onSuccess(Object result) {
                            Config.toast(FoodDetailActivity.this,"successfuly add daily diet to the server");
                        }

                        @Override
                        public void onFail() {
                            Config.toast(FoodDetailActivity.this,"Oops, failed to add daily diet to the server");
                        }
                    });
                }
            });

            builder.setNegativeButton("CANCEL",null);

            builder.show();
        }else{
            Config.toast(this,"food numbers can not be null");
        }
    }
}
