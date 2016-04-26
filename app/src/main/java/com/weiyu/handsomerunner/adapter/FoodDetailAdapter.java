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
import com.weiyu.handsomerunner.domain.FoodItem;

import java.util.List;
import java.util.Random;

/**
 * Created by Sam on 4/17/2016.
 */
public class FoodDetailAdapter extends BaseAdapter {
    private List<FoodItem> foods = null;
    private Context context = null;

    public FoodDetailAdapter(Context context, List<FoodItem> foods){
        this.context = context;
        this.foods = foods;
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
            view = LayoutInflater.from(context).inflate(R.layout.food_detail_item,null);
            holder.tvFoodName = (TextView) view.findViewById(R.id.tv_food_name);
            holder.tvFoodDescription = (TextView) view.findViewById(R.id.tv_food_description);
            view.setTag(holder);
        }else{
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }

        FoodItem food = foods.get(position);
        holder.tvFoodName.setText(food.getFoodName());
        holder.tvFoodDescription.setText(food.getFoodDescription());
        return view;
    }

    private static class ViewHolder{
        TextView tvFoodName;
        TextView tvFoodDescription;
    }
}
