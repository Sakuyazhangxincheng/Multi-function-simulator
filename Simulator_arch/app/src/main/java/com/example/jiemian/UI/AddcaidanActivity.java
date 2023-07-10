package com.example.jiemian.UI;

import androidx.annotation.Nullable;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jiemian.R;
import com.example.jiemian.base.BaseActivity;
import com.example.jiemian.bean.Jishi;
import com.example.jiemian.sqlite.JishiDbutils;
import com.wildma.pictureselector.PictureBean;
import com.wildma.pictureselector.PictureSelector;

public class AddcaidanActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_tupian;
    private EditText et_timju;
    String path;
    private TextView commit1;
    int i=0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_addcaidan;
    }

    @Override
    protected void init() {
        et_timju=findViewById(R.id.tv_name);
        iv_tupian=findViewById(R.id.iv_tupian);
        commit1=findViewById(R.id.commit1);
        iv_tupian.setOnClickListener(this);
        commit1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_tupian:
                PictureSelector
                        .create(AddcaidanActivity.this,PictureSelector.SELECT_REQUEST_CODE)
                        .selectPicture();
                break;
            case R.id.commit1:
                String timu=et_timju.getText().toString();
                Jishi medic=new Jishi();
                medic.setImage(path);
                medic.setTimu(timu);
               int i= JishiDbutils.getInstance(getApplicationContext()).insert(medic);
                if(i==0){
            showToast("添加成功");
                finish();
                }else {
                    showToast("添加失败");
                }
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PictureSelector.SELECT_REQUEST_CODE){
            if(data!=null){
                PictureBean pictureBean=data.getParcelableExtra(PictureSelector.PICTURE_RESULT);
                path=pictureBean.getPath();
                Glide.with(this).load(pictureBean.getPath()).into(iv_tupian);
            }
        }
    }

}