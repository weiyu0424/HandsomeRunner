package com.weiyu.handsomerunner.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.weiyu.handsomerunner.Config;
import com.weiyu.handsomerunner.R;
import com.weiyu.handsomerunner.serice.LoginService;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText etUserName = null;
    private EditText etPassword = null;
    private Button btSignIn = null;
    private TextView tvSignUp = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initEvent();


        //if register succeed, the userName will be pass to the login activity
        Intent intent = getIntent();
        if(intent != null){
            String userName = intent.getStringExtra("userName");
            etUserName.setText(userName);
        }
    }

    private void initView() {
        etUserName = (EditText) findViewById(R.id.et_userName);
        etPassword = (EditText) findViewById(R.id.et_password);
        btSignIn = (Button) findViewById(R.id.bt_sign_in);
        tvSignUp = (TextView) findViewById(R.id.tv_sign_up);
    }

    private void initEvent() {
        btSignIn.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.bt_sign_in:
                //if a user clicks the sign in button, signIn method will be called
                signIn();
                break;
            case R.id.tv_sign_up:
                //if a user clicks the sign up button, app will redirect to a sign up activity
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }


    /**
     * extract a sign in method which can handle the login operations
     */
    private void signIn() {
        final String userName = etUserName.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if(!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(password)){
            LoginService loginService = new LoginService();
            loginService.login(userName, password, new LoginService.LoginCallback() {
                @Override
                public void onSuccess(String result) {
                    /**
                     * the server will return a status code:
                     * 0: means user name does not exist.
                     * 1: means the user has successfully signed in
                     * 2: means the password is wrong
                     */
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int status = Integer.parseInt(jsonObject.getString("status"));
                        if(1 == status){
                            Config.toast(LoginActivity.this,"login success");
                            //after login, we should set the login status as true
                            Config.setLogin(LoginActivity.this,userName);

                            //redirect to the home activity after successfully login
                            Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                            startActivity(intent);
                            finish();

                        }else if(0 == status){
                            Config.toast(LoginActivity.this,"user name doesn't exist!");
                        }else if(2 == status){
                            Config.toast(LoginActivity.this,"the password is wrong!");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFail() {
                    Config.toast(LoginActivity.this,"Oops, login failed!");
                }
            });
        }
    }
}
