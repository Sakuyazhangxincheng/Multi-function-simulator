package com.example.jiemian.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiemian.R;
import com.example.jiemian.Utils.utils1;
import com.example.jiemian.base.BaseActivity;
import com.example.jiemian.sqlite.SqliteDBUtils;

import butterknife.BindView;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.tv_register)
    TextView tvRegister;

    @BindView(R.id.tv_login)
    TextView tvLogin;
    private int state=0;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }
    @Override
    protected void init() {
        tvRegister.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_register:
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                return;
            case R.id.tv_login:
              String name=etPhone.getText().toString().trim();
                String pwd=etPwd.getText().toString().trim();
               if(TextUtils.isEmpty(name)||TextUtils.isEmpty(pwd)){
                   showToast("请检查输入内容");
                   return;
               }   else {
                   int i = SqliteDBUtils.getInstance(LoginActivity.this).Quer(name, pwd);
                   if (i==1) {
                       utils1.passname = name;
                       utils1.passpwd = pwd;
                       Intent intent2 = new Intent(LoginActivity.this, MainActivity.class);
                       startActivity(intent2);
                       showToast("登录成功");
                       finish();
                   }else if(i==-1){
                       showToast("密码错误");
                       return;
                   }else {
                       showToast("无此用户");
                       return;
                   }
               }
        }
    }

}