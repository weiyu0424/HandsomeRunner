package com.weiyu.handsomerunner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.weiyu.handsomerunner.R;
import com.weiyu.handsomerunner.domain.DailyDiet;
import com.weiyu.handsomerunner.domain.Food;

import java.util.List;
import java.util.Random;

/**
 * Created by Sam on 5/3/2016.
 */
public class DailyDietAdapter extends BaseAdapter {
    private List<DailyDiet> DailyDiets = null;
    private Context context = null;

    public DailyDietAdapter(Context context, List<DailyDiet> DailyDiets){
        this.context = context;
        this.DailyDiets = DailyDiets;
    }


    @Override
    public int getCount() {
        return DailyDiets.size();
    }

    @Override
    public Object getItem(int position) {
        return DailyDiets.get(position);
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
            view = LayoutInflater.from(context).inflate(R.layout.daily_diet_item,null);
            holder.ivFoodThumb = (ImageView) view.findViewById(R.id.iv_food_thumb);
            holder.tvFoodName = (TextView) view.findViewById(R.id.tv_food_name);
            holder.tvCounts = (TextView) view.findViewById(R.id.tv_counts);
            holder.tvUpdateTime = (TextView) view.findViewById(R.id.tv_update_time);
            view.setTag(holder);
        }else{
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }


        DailyDiet dailyDiet = DailyDiets.get(position);
        holder.tvFoodName.setText(dailyDiet.getFoodName());
        holder.tvCounts.setText("Counts:" + dailyDiet.getCount());
        holder.tvUpdateTime.setText(dailyDiet.getUpdateTime());
        Picasso.with(context).load(dailyDiet.getFoodImageUrl()).into(holder.ivFoodThumb);
        return view;
    }

    private static class ViewHolder{
        ImageView ivFoodThumb;
        TextView tvFoodName;
        TextView tvCounts;
        TextView tvUpdateTime;
    }
}
