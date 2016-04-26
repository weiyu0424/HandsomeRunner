package com.weiyu.handsomerunner.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weiyu.handsomerunner.Config;
import com.weiyu.handsomerunner.R;
import com.weiyu.handsomerunner.network.NetworkUtils;
import com.weiyu.handsomerunner.service.GoaledCalorieService;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class CalorieTrackFragment extends Fragment {
    private TextView tvCalorieGoalDescription = null;
    private TextView tvCalorieGoal = null;
    private View view = null;
    private TextView tvSteps = null;
    private TextView tvConsumedCalorie = null;
    private TextView tvBurnedCalorie = null;
    public CalorieTrackFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_calorie_track, container, false);
        initView();
        initData();
        return view;
    }

    private void initView() {
        tvCalorieGoalDescription = (TextView) view.findViewById(R.id.tv_calorie_goal_description);
        tvCalorieGoal = (TextView) view.findViewById(R.id.tv_calorie_goal);
        tvConsumedCalorie = (TextView) view.findViewById(R.id.tv_calorie_consumed);
        tvBurnedCalorie = (TextView) view.findViewById(R.id.tv_calorie_burned);
        tvSteps = (TextView) view.findViewById(R.id.tv_steps_of_calorie_track);
    }



    private void initData() {
        //set steps
        int totalSteps = Config.getTotalSteps(getActivity());
        tvSteps.setText(String.valueOf(totalSteps));

        if(NetworkUtils.isNetworkAvailable(getActivity())){
            GoaledCalorieService goaledCalorieService = new GoaledCalorieService();
            /**
             * get goaled calorie
             */
            goaledCalorieService.getGoaledCalorie(Config.getUserName(getActivity()), Config.today(), new GoaledCalorieService.GoaledCalorieCallback() {
                @Override
                public void onSuccess(Object obj) {
                    String goaledCalorie = (String) obj;
                    tvCalorieGoal.setText(goaledCalorie);
                }

                @Override
                public void onFail() {

                }

                @Override
                public void onEmpty() {

                }
            });


            /**
             * get consumed calorie
             */
            goaledCalorieService.getConsumedAndBurnedCalories("Alex", "2016-03-24", new GoaledCalorieService.GoaledCalorieCallback() {
                @Override
                public void onSuccess(Object obj) {
                    String result = (String) obj;
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String consumedCalories = jsonObject.getString("consumedCalories");
                        String burnedCalories = jsonObject.getString("burnedCalories");
                        System.out.println(consumedCalories + ":" + burnedCalories);
                        DecimalFormat df = new DecimalFormat("0.00");
                        tvConsumedCalorie.setText(df.format(Double.parseDouble(consumedCalories)));
                        tvBurnedCalorie.setText(df.format(Double.parseDouble(burnedCalories)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFail() {

                }

                @Override
                public void onEmpty() {

                }
            });

        }else{
            Config.toast(getActivity(),"Oops, current network does not work!");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }
}
