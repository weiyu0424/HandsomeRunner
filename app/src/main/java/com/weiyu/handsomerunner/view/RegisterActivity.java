package com.weiyu.handsomerunner.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.weiyu.handsomerunner.Config;
import com.weiyu.handsomerunner.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText etUserName = null;
    private EditText etPassword = null;
    private EditText etPasswordConfirm = null;
    private Button btNextStep = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initEvent();
    }

    private void initView() {
        etUserName = (EditText) findViewById(R.id.et_userName);
        etPassword = (EditText) findViewById(R.id.et_password);
        etPasswordConfirm = (EditText) findViewById(R.id.et_password_confirm);
        btNextStep = (Button) findViewById(R.id.bt_next_step);
    }


    private void initEvent() {
        btNextStep.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_next_step:
                //go to the next step to fill in more detail about the user information
                nextStep();
                break;
        }
    }

    private void nextStep() {
        String userName = etUserName.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String passwordConfirm = etPasswordConfirm.getText().toString().trim();

        //to make some pre-judgement before entering the next Activity
        if(TextUtils.isEmpty(userName)){
            Config.toast(this,"Honey, user name can not be empty!");
        }else if(TextUtils.isEmpty(password) || TextUtils.isEmpty(passwordConfirm)){
            Config.toast(this,"password can not be empty");
        }else if(!password.equals(passwordConfirm)){
            Config.toast(this,"the two passwords are not consistent");
        }else{
            Intent intent = new Intent(RegisterActivity.this,DataEntryActivity.class);
            intent.putExtra("userName",userName);
            intent.putExtra("password",password);
            startActivity(intent);
            finish();
        }
    }
}
