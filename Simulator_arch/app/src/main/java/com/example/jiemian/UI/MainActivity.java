package com.example.jiemian.UI;

import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.jiemian.R;
import com.example.jiemian.adapter.Myadapter;
import com.example.jiemian.base.BaseActivity;
import com.example.jiemian.fragment.HomeFragment;
import com.example.jiemian.fragment.KuaijieFragment;
import com.example.jiemian.fragment.UserFragment;
import com.example.jiemian.view.NoScrollViewPager;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.radioGroup)
RadioGroup radioGroup;
    @BindView(R.id.main_pager)
    NoScrollViewPager mainpager;
    Myadapter myadapter;
    private long clickTime;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        List<Fragment>fragments=new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new KuaijieFragment());
        fragments.add(new UserFragment());
        myadapter=new Myadapter(getSupportFragmentManager(),fragments);
        mainpager.setOffscreenPageLimit(1);
        mainpager.setAdapter(myadapter);
        radioGroup.setOnCheckedChangeListener(this);
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.tab_home:
                mainpager.setCurrentItem(0,false);
                break;
            case R.id.tab_dynamic:
                mainpager.setCurrentItem(1,false);
                break;
            case R.id.tab_me:
                mainpager.setCurrentItem(2,false);
                break;
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    private void exit() {
        if ((System.currentTimeMillis() - clickTime) > 2000) {
            Toast.makeText(this, "再按一次后退键退出程序", Toast.LENGTH_SHORT).show();
            clickTime = System.currentTimeMillis();
        } else {
            try {
                //正常退出
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}