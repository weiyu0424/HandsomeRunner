package com.weiyu.handsomerunner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.weiyu.handsomerunner.R;
import com.weiyu.handsomerunner.domain.Food;

import java.util.List;
import java.util.Random;

/**
 * Created by Sam on 4/17/2016.
 */
public class FoodItemAdapter extends BaseAdapter {
    private List<Food> foods = null;
    private Context context = null;
    private int[] images = null;

    public FoodItemAdapter(Context context, List<Food> foods, int[] images){
        this.context = context;
        this.foods = foods;
        this.images = images;
    }


    @Override
    public int getCount() {
        return foods.size();
    }

    @Override
    public Object getItem(int position) {
        return foods.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
//            view = LayoutInflater.from()
            view = LayoutInflater.from(context).inflate(R.layout.food_item,null);
            holder.ivFoodThumb = (ImageView) view.findViewById(R.id.iv_food_thumb);
            holder.tvFoodName = (TextView) view.findViewById(R.id.tv_food_name);
            holder.tvFatDescription = (TextView) view.findViewById(R.id.tv_fat_description);
            holder.tvCaloriesDescription = (TextView) view.findViewById(R.id.tv_calories_description);
            view.setTag(holder);
        }else{
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }


        Random random = new Random();
        Food food = foods.get(position);
        holder.tvFoodName.setText(food.getFoodName());
        holder.tvFatDescription.setText("fat:" + food.getFat() + "g");
        holder.tvCaloriesDescription.setText("calories:" + food.getCalories());
        holder.ivFoodThumb.setImageResource(images[random.nextInt(images.length)]);
        return view;
    }

    private static class ViewHolder{
        ImageView ivFoodThumb;
        TextView tvFoodName;
        TextView tvFatDescription;
        TextView tvCaloriesDescription;
    }
}
