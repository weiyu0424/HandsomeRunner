package com.weiyu.handsomerunner.view;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.weiyu.handsomerunner.R;
import com.weiyu.handsomerunner.fragment.CalorieGoalFragment;
import com.weiyu.handsomerunner.fragment.CalorieTrackFragment;
import com.weiyu.handsomerunner.fragment.HomeFragment;
import com.weiyu.handsomerunner.fragment.NearByFragment;
import com.weiyu.handsomerunner.fragment.ProgressReportFragment;
import com.weiyu.handsomerunner.fragment.StepsFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener{
    private DrawerLayout drawer = null;
    private NavigationView navigationView = null;
    private CalorieGoalFragment calorieGoalFragment = null;
    private CalorieTrackFragment calorieTrackFragment = null;
    private HomeFragment homeFragment = null;
    private NearByFragment nearByFragment = null;
    private ProgressReportFragment progressReportFragment = null;
    private StepsFragment stepsFragment = null;


    private LinearLayout llHomePage = null;
    private LinearLayout llCalorieTrackPage = null;
    private LinearLayout llProgressReportPage = null;

    private ImageView ivTabHome = null;
    private ImageView ivTabCalorieTrack = null;
    private ImageView ivTabProgressReport = null;

    private TextView tvHome = null;
    private TextView tvCalorieTrack = null;
    private TextView tvProgressReport = null;

    private FragmentManager fm = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initView();
        initEvent();


        /**
         * set a drawerListener when open or close the navigation bar
         */
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        fm = getSupportFragmentManager();

        setTabSelection(0);
    }


    /**
     * bind the widget in the layout file
     */
    private void initView() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        llHomePage = (LinearLayout) findViewById(R.id.home_page);
        llCalorieTrackPage = (LinearLayout) findViewById(R.id.calorie_track_page);
        llProgressReportPage = (LinearLayout) findViewById(R.id.progress_report_page);

        ivTabHome = (ImageView) findViewById(R.id.iv_tab_home);
        ivTabCalorieTrack = (ImageView) findViewById(R.id.iv_tab_calorie_track);
        ivTabProgressReport = (ImageView) findViewById(R.id.iv_tab_progress_report);

        tvHome = (TextView) findViewById(R.id.tv_home);
        tvCalorieTrack = (TextView) findViewById(R.id.tv_calorie_track);
        tvProgressReport = (TextView) findViewById(R.id.tv_progress_report);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
    }

    /**
     * initiate events for widgets
     */
    private void initEvent() {
        llHomePage.setOnClickListener(this);
        llCalorieTrackPage.setOnClickListener(this);
        llProgressReportPage.setOnClickListener(this);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {
        /**
         * when press button, if the drawer is open, close it first, else exit from the app
         */
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.calorie_goal:
                setTabSelection(3);
                break;
            case R.id.steps:
                setTabSelection(4);
                break;
            case R.id.nearby:
                setTabSelection(5);
                break;
            case R.id.setting:
                break;
        }

        //after selecting the menu item, we should close the drawer layout
        /*if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }*/

        return false;
    }


    /**
     * clear the color of fonts and set background of ImageView as default image.
     */
    public void clearTextColor(){
//        tvHomePage.setTextColor(Color.parseColor("#00C5CD"));
        tvHome.setTextColor(getResources().getColor(R.color.normal_color));
        tvCalorieTrack.setTextColor(getResources().getColor(R.color.normal_color));
        tvProgressReport.setTextColor(getResources().getColor(R.color.normal_color));

        ivTabHome.setImageResource(R.mipmap.icon_home);
        ivTabCalorieTrack.setImageResource(R.mipmap.icon_calorie_track);
        ivTabProgressReport.setImageResource(R.mipmap.icon_report);
    }


    /**
     * hide the old fragment before displaying the new fragment
     * @param transaction
     */
    private void hideFragment(FragmentTransaction transaction) {
        if(homeFragment != null){
            transaction.hide(homeFragment);
        }
        if(calorieTrackFragment != null){
            transaction.hide(calorieTrackFragment);
        }

        if(progressReportFragment != null){
            transaction.hide(progressReportFragment);
        }

    }

    /**
     * a method which display different fragment with different index
     * @param index: the index which need to display in the FrameLayout Widget
     */
    private void setTabSelection(int index){
        Intent intent = null;
        //set the ImageView as normal image and set TextView as normal color
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragment(transaction);
        ActionBar actionBar = getSupportActionBar();
        switch (index){
            case 0:
                clearTextColor();
                if(actionBar != null) {
                    actionBar.setTitle("Runner");
                }
                tvHome.setTextColor(getResources().getColor(R.color.home_color));
                ivTabHome.setImageResource(R.mipmap.icon_home_pressed);
                if (homeFragment == null) {
                    homeFragment = HomeFragment.newInstance();
                    transaction.add(R.id.fl_container,homeFragment);
                }else{
                    transaction.show(homeFragment);
                }
//                transaction.replace(R.id.container, homeFragment,"HomeFragment");
                break;
            case 1:
                clearTextColor();
                if(actionBar != null) {
                    actionBar.setTitle("Calorie Track");
                }
                tvCalorieTrack.setTextColor(getResources().getColor(R.color.home_color));
                ivTabCalorieTrack.setImageResource(R.mipmap.icon_calorie_track_pressed);
                if (calorieTrackFragment == null) {
                    calorieTrackFragment = new CalorieTrackFragment();
                    transaction.add(R.id.fl_container,calorieTrackFragment);
                }else{
                    transaction.show(calorieTrackFragment);
                }
//                transaction.replace(R.id.container, courseFragment, "CourseFragment");
                break;
            case 2:
                clearTextColor();
                getSupportActionBar().setTitle("Progress Report");
//                tintManager.setStatusBarTintResource(R.color.user_color);
                tvProgressReport.setTextColor(getResources().getColor(R.color.home_color));
                ivTabProgressReport.setImageResource(R.mipmap.icon_report_pressed);
                if (progressReportFragment == null) {
                    progressReportFragment = ProgressReportFragment.newInstance();
//                    discoverFragment = new DiscoverFragment();
                    transaction.add(R.id.fl_container, progressReportFragment);
                }else {
                    transaction.show(progressReportFragment);
                }
                break;

            case 3:
                /*if (calorieGoalFragment == null) {
                    calorieGoalFragment = new CalorieGoalFragment();
//                    discoverFragment = new DiscoverFragment();
                    transaction.add(R.id.fl_container, calorieGoalFragment);
                }else {
                    transaction.show(calorieGoalFragment);
                }*/

                intent = new Intent(this,CalorieGoalActivity.class);
                startActivity(intent);
                break;
            case 4:
                intent = new Intent(this,StepsActivity.class);
                startActivity(intent);
                /*if (stepsFragment == null) {
                    stepsFragment = new StepsFragment();
//                    discoverFragment = new DiscoverFragment();
                    transaction.add(R.id.fl_container, stepsFragment);
                }else {
                    transaction.show(stepsFragment);
                }*/
                break;
            case 5:
                /*if (nearByFragment == null) {
                    nearByFragment = new NearByFragment();
//                    discoverFragment = new DiscoverFragment();
                    transaction.add(R.id.fl_container, nearByFragment);
                }else {
                    transaction.show(nearByFragment);
                }*/

                intent = new Intent(this,NearByActivity.class);
                startActivity(intent);
                break;
        }
        transaction.commit();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_page:
                //if the home page icon is selected,replace the FrameLayout with HomeFragment
                setTabSelection(0);
                break;
            case R.id.calorie_track_page:
                //if the calorie track page icon is selected,replace the FrameLayout with CalorieTrackPage
                setTabSelection(1);
                break;
            case R.id.progress_report_page:
                //if the progress report page icon is selected,replace the FrameLayout with ProgressReport
                setTabSelection(2);
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        setTabSelection(0);
    }
}
