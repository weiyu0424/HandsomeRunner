package com.weiyu.handsomerunner.view;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.weiyu.handsomerunner.Config;
import com.weiyu.handsomerunner.R;
import com.weiyu.handsomerunner.db.DBCalorieGoalHandler;
import com.weiyu.handsomerunner.network.NetworkUtils;
import com.weiyu.handsomerunner.service.GoaledCalorieService;

public class CalorieGoalActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tvCalorieGoalHint = null;
    private EditText etCalorieAdd = null;
    private Button btCalorieAdd = null;
    private TextView tvCalorie = null;
    private LinearLayout llCalorieAdd = null;
    private RelativeLayout rlCalorieShow = null;
    private DBCalorieGoalHandler dbCalorieGoalHandler = null;
    private GoaledCalorieService goaledCalorieService = null;

    private ImageView ivBack = null;
    private TextView tvEdit = null;
    /**
     * there are three steps
     * 0: means click edit button
     * 1: means normal status
     * 2: means have not set calorie goal yet
     */
    private int status = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_goal);
        //initToolbar();

        initView();

        initData();

        initEvent();

    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Calorie Goal");
//        toolbar.setTitle(null);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_return);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        tvCalorieGoalHint = (TextView) findViewById(R.id.tv_calorie_goal_hint);
        etCalorieAdd = (EditText) findViewById(R.id.et_calorie_add);
        btCalorieAdd = (Button) findViewById(R.id.bt_calorie_goal_add);
        tvCalorie = (TextView) findViewById(R.id.tv_calorie);
        llCalorieAdd = (LinearLayout) findViewById(R.id.ll_calorie_add);
        rlCalorieShow = (RelativeLayout) findViewById(R.id.rl_calorie_show);


        ivBack = (ImageView) findViewById(R.id.iv_back_of_calorie_goal);
        tvEdit = (TextView) findViewById(R.id.tv_edit);

        goaledCalorieService = new GoaledCalorieService();
        dbCalorieGoalHandler = new DBCalorieGoalHandler(CalorieGoalActivity.this);
    }


    /**
     * a method to initialize the calorie goal
     */
    private void initData() {
        /**
         * if the current network is available, get the goaled calories from the server
         * else retrieve the data from local database
         */
        if(NetworkUtils.isNetworkAvailable(this)){
            goaledCalorieService.getGoaledCalorie(Config.getUserName(this), Config.today(), new GoaledCalorieService.GoaledCalorieCallback() {
                @Override
                public void onSuccess(Object obj) {
                    String calorie = (String) obj;
                    tvEdit.setVisibility(View.VISIBLE);
                    rlCalorieShow.setVisibility(View.VISIBLE);
                    llCalorieAdd.setVisibility(View.GONE);
                    tvCalorieGoalHint.setText("Today, your calorie goal is:");
                    tvCalorie.setText(calorie);
//                    Config.toast(CalorieGoalActivity.this,"success");
                }

                @Override
                public void onFail() {
                    Config.toast(CalorieGoalActivity.this,"Oops, failed to retrieve the goaled calories");
                }

                @Override
                public void onEmpty() {
//                    Config.toast(CalorieGoalActivity.this,"empty");
                    status = 2;
                    rlCalorieShow.setVisibility(View.GONE);
                    llCalorieAdd.setVisibility(View.VISIBLE);
                    tvCalorieGoalHint.setText("You Haven't set calorie goal yet!");
                }
            });
        }else {

            double calorie = dbCalorieGoalHandler.getCalorie();
            if (calorie != 0) {
                rlCalorieShow.setVisibility(View.VISIBLE);
                tvEdit.setVisibility(View.VISIBLE);
                llCalorieAdd.setVisibility(View.GONE);
                tvCalorieGoalHint.setText("Today, your calorie goal is:");
                tvCalorie.setText(String.valueOf(calorie));
            } else {
                status = 2;
                rlCalorieShow.setVisibility(View.GONE);
                llCalorieAdd.setVisibility(View.VISIBLE);
                tvCalorieGoalHint.setText("You Haven't set calorie goal yet!");
            }
        }
    }



    private void initEvent() {
        btCalorieAdd.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        tvEdit.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_history:

                break;
            case R.id.action_edit:
                if(2 == status){
                    Config.toast(this,"you have not set your calorie goal yet!");
                }else {
                    llCalorieAdd.setVisibility(View.VISIBLE);
                    btCalorieAdd.setText("UPDATE");
                    //isUpdate = true;
                    status = 0;
                }
//                AlertDialog.Builder builder = new AlertDialog.Builder(this).;
                break;
        }
        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_calorie_goal_add:
                final String calorie = etCalorieAdd.getText().toString();
                if(!TextUtils.isEmpty(calorie)){
                    AddAndUpdateCalorieGoal(calorie);
                }else{
                    Config.toast(CalorieGoalActivity.this,"You should not set an empty calorie goal!");
                }
                break;

            case R.id.iv_back_of_calorie_goal:
                finish();
                break;

            case R.id.tv_edit:
                if(2 == status){
                    Config.toast(this,"you have not set your calorie goal yet!");
                }else {
                    llCalorieAdd.setVisibility(View.VISIBLE);
                    btCalorieAdd.setText("UPDATE");
                    status = 0;
                }
                break;
        }
    }


    /**
     * in this method, We can add or update our calorie goal
     * @param calorie: the calorie we want to add or update
     */
    private void AddAndUpdateCalorieGoal(final String calorie) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(0 == status){
            builder.setTitle("Update Warning").setMessage("Do you really want to update your calorie goal!");
        }else{
            builder.setTitle("Add Warning").setMessage("Do you really want to add your calorie goal!");
        }

        builder.setPositiveButton("Agree", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(0 != status) {
                    goaledCalorieService.insertGoaledCalories(Config.getUserName(CalorieGoalActivity.this), Config.today(), calorie, new GoaledCalorieService.GoaledCalorieCallback() {
                        @Override
                        public void onSuccess(Object obj) {

                            tvEdit.setVisibility(View.VISIBLE);
                            dbCalorieGoalHandler.insertCalorie(Double.parseDouble(calorie));
                            Config.toast(CalorieGoalActivity.this,"You have successfully set the calorie goal!");

                            //after successfully inserting the calorie goal, set status = 1;
                            status = 1;
                        }

                        @Override
                        public void onFail() {
                            Config.toast(CalorieGoalActivity.this,"Oops, failed to set the calorie goal!");
                        }

                        @Override
                        public void onEmpty() {

                        }
                    });

                }else{

                    goaledCalorieService.updateGoaledCalories(Config.getUserName(CalorieGoalActivity.this), Config.today(), calorie, new GoaledCalorieService.GoaledCalorieCallback() {
                        @Override
                        public void onSuccess(Object obj) {
                            dbCalorieGoalHandler.updateCalorie(Double.parseDouble(calorie));
                            Config.toast(CalorieGoalActivity.this,"You have successfully update the calorie goal!");
                        }

                        @Override
                        public void onFail() {
                            Config.toast(CalorieGoalActivity.this,"Oops, failed to update the calorie goal!");
                        }

                        @Override
                        public void onEmpty() {

                        }
                    });

                }
                rlCalorieShow.setVisibility(View.VISIBLE);
                tvCalorie.setText(calorie);
            }
        });

        builder.setNegativeButton("Cancel",null);
        builder.show();
    }


}
