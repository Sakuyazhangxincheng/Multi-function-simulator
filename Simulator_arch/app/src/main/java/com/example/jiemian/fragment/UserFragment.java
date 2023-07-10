package com.example.jiemian.fragment;

import android.content.Intent;

import android.view.View;
import android.widget.RelativeLayout;

import com.example.jiemian.R;
import com.example.jiemian.UI.LoginActivity;
import com.example.jiemian.base.LazyFragment;

import butterknife.BindView;

public class UserFragment extends LazyFragment implements View.OnClickListener {
    @BindView(R.id.rl_userinfo)
    RelativeLayout rlUserinfo;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void loadData() {
        rlUserinfo.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_userinfo:
                Intent intent=new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;

        }
    }
}