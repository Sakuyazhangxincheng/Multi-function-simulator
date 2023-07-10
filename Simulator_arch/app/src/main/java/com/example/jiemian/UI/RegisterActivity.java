package com.example.jiemian.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jiemian.R;
import com.example.jiemian.Utils.utils1;
import com.example.jiemian.base.BaseActivity;
import com.example.jiemian.bean.User;
import com.example.jiemian.sqlite.SqliteDBUtils;

import butterknife.BindView;
import butterknife.internal.Utils;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_pwd1)
    EditText etPwd1;
    @BindView(R.id.rl_back)
    RelativeLayout rlback;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.tv_next_step)
    TextView tvNextStep;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void init() {
        tvNextStep.setOnClickListener(this);
        rlback.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_next_step:
                String name=  etName.getText().toString().trim();
                String pwd=  etPwd.getText().toString().trim();
                String pwd1=  etPwd1.getText().toString().trim();
                if(TextUtils.isEmpty(name)||TextUtils.isEmpty(pwd)||TextUtils.isEmpty(pwd1)){
                    showToast("请检查输入内容");
                    return;
                }else if(!pwd.equals(pwd1)){
                    showToast("两次密码不一致");
                    return;
                }else if(pwd1.length()<2||pwd1.length()>10){
                    showToast("密码长度要在2到10个字符");
                    return;
                }else {
                    SharedPreferences preferences = getSharedPreferences("user",MODE_PRIVATE);
                    SharedPreferences.Editor edit = preferences.edit();
                    edit.putString("name",name);
                    edit.putString("pwd",pwd);
                    edit.commit();

                    utils1.passpwd=pwd;
                    utils1.passname=name;
                    User user=new User();
                    user.setUsername(name);
                    user.setUserpwd(pwd);
                    change(user);

            }        }
    }

    private void change(User user) {
        AlertDialog.Builder builder=new AlertDialog.Builder(RegisterActivity.this);
        builder.setTitle("提示")
                .setMessage("确定注册吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SqliteDBUtils.getInstance(RegisterActivity.this).saveUser(user);
                        Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                    showToast("注册成功");
                    dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }
}