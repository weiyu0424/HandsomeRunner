package com.weiyu.handsomerunner.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.weiyu.handsomerunner.Config;
import com.weiyu.handsomerunner.R;
import com.weiyu.handsomerunner.adapter.StepsAdapter;
import com.weiyu.handsomerunner.db.DBStepsHandler;
import com.weiyu.handsomerunner.domain.Steps;
import com.weiyu.handsomerunner.service.StepsService;

import java.util.List;

public class StepsActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView ivBackOfSetSteps = null;
    private LinearLayout llStepsAdd = null;
    private EditText etStepsAdd = null;
    private Button btStepsAdd = null;
    private ListView lvStepsRecord = null;
    private TextView tvCalculate = null;
    private RelativeLayout rlStepsShow = null;
    private TextView tvTotalSteps = null;
    private TextView tvStepsRecord = null;
    private DBStepsHandler dbStepsHandler = null;
    private StepsAdapter adapter = null;
    private int previousSteps = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);

        initView();
        initData();
        initEvent();

    }

    private void initView() {
        ivBackOfSetSteps = (ImageView) findViewById(R.id.iv_back_of_set_steps);
        llStepsAdd = (LinearLayout) findViewById(R.id.ll_steps_add);
        etStepsAdd = (EditText) findViewById(R.id.et_steps_add);
        btStepsAdd = (Button) findViewById(R.id.bt_steps_add);
        lvStepsRecord = (ListView) findViewById(R.id.lv_steps_record);
        tvCalculate  = (TextView) findViewById(R.id.tv_calculate);
        rlStepsShow = (RelativeLayout) findViewById(R.id.rl_steps_show);
        tvTotalSteps = (TextView) findViewById(R.id.tv_total_steps);
        tvStepsRecord = (TextView) findViewById(R.id.tv_steps_record);
        dbStepsHandler = new DBStepsHandler(this);
    }

    private void initEvent() {
        btStepsAdd.setOnClickListener(this);
        ivBackOfSetSteps.setOnClickListener(this);
        tvCalculate.setOnClickListener(this);
    }


    private void initData() {
        List<Steps> steps = dbStepsHandler.queryStepsByTime(Config.today());
        if(steps.size() != 0){
            tvCalculate.setVisibility(View.VISIBLE);
        }
        adapter = new StepsAdapter(this,steps);
        lvStepsRecord.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back_of_set_steps:
                finish();
                break;
            case R.id.bt_steps_add:
                addSteps();
                break;

            case R.id.tv_calculate:
                List<Steps> steps = dbStepsHandler.queryStepsByTime(Config.today());
                int totalSteps = 0;
                for(Steps step : steps){
                    totalSteps += step.getSteps();
                }

                //if total steps have not changed, it doesn't need to to synchronized the server
                if(Config.getTotalSteps(this) != totalSteps){
                    new StepsService().insertSteps(Config.getUserName(this), Config.today(), totalSteps + "", new StepsService.StepsServiceCallback() {
                        @Override
                        public void onSuccess(Object obj) {
                            Config.toast(StepsActivity.this,"have successfully synchronized to the server");
                        }

                        @Override
                        public void onFail() {

                        }
                    });
                }/*else{
                    Config.toast(StepsActivity.this,"same steps");
                }*/

                //cache the total steps
                Config.setTotalSteps(this,totalSteps);
                rlStepsShow.setVisibility(View.VISIBLE);
                lvStepsRecord.setVisibility(View.GONE);
                tvStepsRecord.setVisibility(View.GONE);
                tvTotalSteps.setText(String.valueOf(totalSteps));
                break;
        }
    }


    /**
     * create a method to add step item into database
     */
    private void addSteps() {
        String steps = etStepsAdd.getText().toString().trim();
        if(!TextUtils.isEmpty(steps)){
            rlStepsShow.setVisibility(View.GONE);
            tvCalculate.setVisibility(View.VISIBLE);
            lvStepsRecord.setVisibility(View.VISIBLE);
            tvStepsRecord.setVisibility(View.VISIBLE);
            dbStepsHandler.insertSteps(Integer.parseInt(steps));
            List<Steps> newSteps = dbStepsHandler.queryStepsByTime(Config.today());
            adapter.setSteps(newSteps);
        }else{
            Config.toast(this,"steps record can not be null");
        }
    }
}
