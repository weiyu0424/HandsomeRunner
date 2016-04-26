package com.weiyu.handsomerunner.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weiyu.handsomerunner.Config;
import com.weiyu.handsomerunner.R;
import com.weiyu.handsomerunner.view.FoodItemActivity;

public class HomeFragment extends Fragment implements View.OnClickListener{
    private TextView tvWelcome = null;
    private TextView tvCurrentTime = null;
    private View view = null;

    private TextView tvDrinks = null;
    private TextView tvMeal = null;
    private TextView tvMeat = null;
    private TextView tvSnacks = null;
    private TextView tvBreads = null;
    private TextView tvCakes = null;
    private TextView tvFruits = null;
    private TextView tvVegetables = null;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        initView();
        initEvent();
        initData();
        return view;
    }

    private void initView() {
        tvWelcome = (TextView) view.findViewById(R.id.tv_welcome);
        tvCurrentTime = (TextView) view.findViewById(R.id.tv_current_time);
        tvDrinks = (TextView) view.findViewById(R.id.tv_drinks);
        tvMeal = (TextView) view.findViewById(R.id.tv_meal);
        tvMeat = (TextView) view.findViewById(R.id.tv_meat);
        tvSnacks = (TextView) view.findViewById(R.id.tv_snacks);
        tvBreads = (TextView) view.findViewById(R.id.tv_breads);
        tvCakes = (TextView) view.findViewById(R.id.tv_cakes);
        tvFruits = (TextView) view.findViewById(R.id.tv_fruits);
        tvVegetables = (TextView) view.findViewById(R.id.tv_vegetables);
    }

    private void initEvent() {
        tvDrinks.setOnClickListener(this);
        tvMeal.setOnClickListener(this);
        tvMeat.setOnClickListener(this);
        tvSnacks.setOnClickListener(this);
        tvCakes.setOnClickListener(this);
        tvFruits.setOnClickListener(this);
        tvBreads.setOnClickListener(this);
        tvVegetables.setOnClickListener(this);
    }


    private void initData() {

        //display the user name
        String userName = Config.getUserName(getActivity());
        if(!TextUtils.isEmpty(userName)){
            tvWelcome.setText("Welcome, " + userName);
        }


        //display the date
        String currentTime = Config.currentTime();
        if(!TextUtils.isEmpty(currentTime)){
            tvCurrentTime.setText("Today is: " + currentTime);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), FoodItemActivity.class);
        switch (v.getId()){
            case R.id.tv_drinks:
                intent.putExtra(Config.CATEGORY,"drink");
                break;
            case R.id.tv_meal:
                intent.putExtra(Config.CATEGORY,"meal");
                break;
            case R.id.tv_meat:
                intent.putExtra(Config.CATEGORY,"meat");
                break;
            case R.id.tv_snacks:
                intent.putExtra(Config.CATEGORY,"snack");
                break;
            case R.id.tv_breads:
                intent.putExtra(Config.CATEGORY,"bread");
                break;
            case R.id.tv_cakes:
                intent.putExtra(Config.CATEGORY,"cake");
                break;
            case R.id.tv_fruits:
                intent.putExtra(Config.CATEGORY,"fruit");
                break;
            case R.id.tv_vegetables:
                intent.putExtra(Config.CATEGORY,"vegetable");
                break;
        }
        startActivity(intent);
    }

}
