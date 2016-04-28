package com.weiyu.handsomerunner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapView;
import com.weiyu.handsomerunner.view.HomeActivity;
import com.weiyu.handsomerunner.view.LoginActivity;

public class MainActivity extends AppCompatActivity {
    private MapView mapView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mapView = (MapView) findViewById(R.id.bmapView);


        Intent intent = null;
        if(Config.isLogin(this)){
            intent = new Intent(this, HomeActivity.class);
        }else{
            intent = new Intent(this, LoginActivity.class);
        }
        startActivity(intent);
        finish();
    }


    /*@Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }*/
}
