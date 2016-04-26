package com.weiyu.handsomerunner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.weiyu.handsomerunner.R;
import com.weiyu.handsomerunner.domain.Steps;

import java.util.List;

/**
 * Created by Sam on 4/17/2016.
 */
public class StepsAdapter extends BaseAdapter {
    private List<Steps> steps = null;
    private Context context = null;

    public StepsAdapter(Context context, List<Steps> steps){
        this.context = context;
        this.steps = steps;
    }

    public void setSteps(List<Steps> steps) {
        this.steps = steps;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return steps.size();
    }

    @Override
    public Object getItem(int position) {
        return steps.get(position);
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
            view = LayoutInflater.from(context).inflate(R.layout.steps_item,null);
            holder.tvSteps = (TextView) view.findViewById(R.id.tv_steps);
            holder.tvUpdateTime = (TextView) view.findViewById(R.id.tv_update_time);
            view.setTag(holder);
        }else{
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }

        Steps step = steps.get(position);
        holder.tvSteps.setText(String.valueOf(step.getSteps()) + " steps");
        holder.tvUpdateTime.setText(step.getUpdateTime());
        return view;
    }

    private static class ViewHolder{
        TextView tvSteps;
        TextView tvUpdateTime;
    }
}
