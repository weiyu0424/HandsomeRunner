package com.weiyu.handsomerunner.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.weiyu.handsomerunner.Config;
import com.weiyu.handsomerunner.R;
import com.weiyu.handsomerunner.adapter.FoodItemAdapter;
import com.weiyu.handsomerunner.domain.Food;
import com.weiyu.handsomerunner.network.NetworkUtils;
import com.weiyu.handsomerunner.service.FoodItemService;
import com.weiyu.handsomerunner.service.FatSecretAPIService;

import java.util.List;

public class FoodItemActivity extends AppCompatActivity implements View.OnClickListener{
    private ListView lvFoodItems = null;
    private LinearLayout llFoodItems = null;
    private ImageView ivLoadOfFoodItems = null;
    private TextView tvLoadOfFoodItems = null;
    private ImageView ivBackFoodItem = null;
    private TextView tvTitleOfItems = null;
    private List<Food> foodItems = null;
    private int[] images = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_item);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        initView();

        //receive the intent from HomeFragment
        Intent intent = getIntent();
        String category = intent.getStringExtra(Config.CATEGORY);

//        getSupportActionBar().setTitle(category + " items");
        tvTitleOfItems.setText(category + " items");
        if (NetworkUtils.isNetworkAvailable(this)) {
            getFoodItemsWithCategory(category);
        } else {
            Config.toast(this, "Oops, the network doesn't work, please enable the mobile data or connect to the WIFI");
        }

        initEvent();
    }

    private void initEvent() {
        ivBackFoodItem.setOnClickListener(this);
    }

    private void initView() {
        lvFoodItems = (ListView) findViewById(R.id.lv_food_items);
        ivLoadOfFoodItems = (ImageView) findViewById(R.id.iv_load_of_food_items);
        tvLoadOfFoodItems = (TextView) findViewById(R.id.tv_load_of_food_items);
        llFoodItems = (LinearLayout) findViewById(R.id.ll_food_items);
        ivLoadOfFoodItems = (ImageView) findViewById(R.id.iv_load_of_food_items);
        tvLoadOfFoodItems = (TextView) findViewById(R.id.tv_load_of_food_items);
        ivBackFoodItem = (ImageView) findViewById(R.id.iv_back_food_item);
        tvTitleOfItems = (TextView) findViewById(R.id.tv_title_of_items);
    }


    /**
     * create a method to retrieve the food items from the server with a certain category
     *
     * @param category: the specific category
     */
    private void getFoodItemsWithCategory(String category) {
        switch (category){
            case "drink":
                images = new int[]{R.drawable.drinks1, R.drawable.drinks2, R.drawable.drinks3,R.drawable.drinks4,R.drawable.drinks5,R.drawable.drinks6,R.drawable.drinks7};
                break;
            case "meal":
                images = new int[]{R.drawable.meal1, R.drawable.meal2, R.drawable.meal3,R.drawable.meal4,R.drawable.meal5,R.drawable.meal6};
                break;
            case "meat":
                images = new int[]{R.drawable.meat1, R.drawable.meat2, R.drawable.meat3,R.drawable.meat4,R.drawable.meat5,R.drawable.meat6,R.drawable.meat7};
                break;
            case "snack":
                images = new int[]{R.drawable.snack1, R.drawable.snack2, R.drawable.snack3,R.drawable.snack4,R.drawable.snack5,R.drawable.snack6,R.drawable.snack7};
                break;
            case "bread":
                images = new int[]{R.drawable.snack1, R.drawable.snack2, R.drawable.snack3,R.drawable.snack4,R.drawable.snack5,R.drawable.snack6,R.drawable.snack7};
                break;
            case "cake":
                images = new int[]{R.drawable.snack1, R.drawable.snack2, R.drawable.snack3,R.drawable.snack4,R.drawable.snack5,R.drawable.snack6,R.drawable.snack7};
                break;
            case "fruit":
                images = new int[]{R.drawable.snack1, R.drawable.snack2, R.drawable.snack3,R.drawable.snack4,R.drawable.snack5,R.drawable.snack6,R.drawable.snack7};
                break;
            case "vegetable":
                images = new int[]{R.drawable.snack1, R.drawable.snack2, R.drawable.snack3,R.drawable.snack4,R.drawable.snack5,R.drawable.snack6,R.drawable.snack7};
                break;
        }
        FoodItemService service = new FoodItemService();
        service.retrieveFoodItems(category, new FoodItemService.FoodItemCallback() {
            @Override
            public void onSuccess(Object result) {
                foodItems = (List<Food>) result;

                //if load food items successfully, make Hint view invisible
                llFoodItems.setVisibility(View.GONE);
                if (result != null) {
                    for (Food food : foodItems) {
                        System.out.println(food);
                    }
                }

                FoodItemAdapter adapter = new FoodItemAdapter(FoodItemActivity.this, foodItems,images);
                lvFoodItems.setAdapter(adapter);
                /**
                 * set item click listener for listview
                 */
                lvFoodItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Food foodItem = foodItems.get(position);
                        Intent intent = new Intent(FoodItemActivity.this,FoodDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("foodItem",foodItem);
                        intent.putExtras(bundle);
                        startActivity(intent);
//                        new FatSecretAPIService().getFoodItems(foodItem.getFoodName());
                        //new SearchService().search(foodItem.getFoodName(), SearchMethod.WEB,null);
                    }
                });
            }

            @Override
            public void onFail() {
//                Toast.makeText(FoodItemActivity.this,"Oops, obtaining food items from the server failed!",Toast.LENGTH_SHORT).show();
                Config.toast(FoodItemActivity.this, "Oops, obtaining food items from the server failed!");
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back_food_item:
                finish();
                break;
        }
    }
}
