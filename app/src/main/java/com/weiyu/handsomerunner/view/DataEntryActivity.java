package com.weiyu.handsomerunner.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.weiyu.handsomerunner.Config;
import com.weiyu.handsomerunner.R;
import com.weiyu.handsomerunner.db.DBUserHandler;
import com.weiyu.handsomerunner.domain.User;
import com.weiyu.handsomerunner.service.RegisterService;

import org.json.JSONException;
import org.json.JSONObject;

public class DataEntryActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText etAge = null;
    private EditText etHeight = null;
    private EditText etWeight = null;
    private RadioButton rbMale = null;
    private RadioButton rbFemale = null;
    private Button btSignUp = null;
    private Spinner spLevel = null;
    private String userName = null;
    private String password = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry);
        initView();
        initEvent();
        Intent intent = getIntent();
        if(intent != null){
            userName = intent.getStringExtra("userName");
            password = intent.getStringExtra("password");
        }
    }

    private void initView() {
        etAge = (EditText) findViewById(R.id.et_age);
        etHeight = (EditText) findViewById(R.id.et_height);
        etWeight = (EditText) findViewById(R.id.et_weight);
        rbMale = (RadioButton) findViewById(R.id.rb_male);
        rbFemale = (RadioButton) findViewById(R.id.rb_female);
        btSignUp = (Button) findViewById(R.id.bt_sign_up);
        spLevel = (Spinner) findViewById(R.id.sp_level);
    }


    private void initEvent() {
        btSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_sign_up:
                signUp();
                break;
        }
    }


    /**
     * create a method to do the signup operations
     */
    private void signUp() {
        String age = etAge.getText().toString().trim();
        String height = etHeight.getText().toString().trim();
        String weight = etWeight.getText().toString().trim();
        String gender = null;
        if(rbMale.isChecked()){
            gender = "male";
        }else{
            gender = "female";
        }

        if(TextUtils.isEmpty(age)){
            Config.toast(this,"age can not be empty");
        }else if(TextUtils.isEmpty(height)){
            Config.toast(this,"height can not be empty");
        }else if(TextUtils.isEmpty(weight)){
            Config.toast(this,"weight can not be empty");
        }else{
            long selectedItemId = spLevel.getSelectedItemId();
            final User user = new User();
            user.setUserName(userName);
            user.setPassword(password);
            user.setAge(Integer.parseInt(age));
            user.setHeight(Double.parseDouble(height));
            user.setWeight(Double.parseDouble(weight));
            user.setLevel((int)(selectedItemId + 1));
            user.setGender(gender);

            //register a user account
            RegisterService registerService = new RegisterService();

            registerService.register(user, new RegisterService.RegisterCallback() {
                @Override
                public void onSuccess(String result) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String status = jsonObject.getString("status");

                        //if the status is 1, it means user account has successfully been registered
                        if(Integer.parseInt(status) == 1){
                            Config.toast(DataEntryActivity.this,"register success");

                            //after successfully registered the user account, backup the user account to the local databse
                            DBUserHandler dbUserHandler = new DBUserHandler(DataEntryActivity.this);
                            dbUserHandler.insertUser(user);


                            /*Intent intent = new Intent(DataEntryActivity.this,LoginActivity.class);
                            intent.putExtra("userName",user.getUserName());

                            //redirect to login activity
                            startActivity(intent);*/
                            finish();
                        }else{
                            Config.toast(DataEntryActivity.this,"the account has already existed");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //System.out.println("result:" + result);
                }

                @Override
                public void onFail() {
                    Config.toast(DataEntryActivity.this,"Oops, register failed, please try again!");
                }
            });


        }
    }
}
