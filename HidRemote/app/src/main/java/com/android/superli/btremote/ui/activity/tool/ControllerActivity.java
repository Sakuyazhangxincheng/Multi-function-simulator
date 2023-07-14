package com.android.superli.btremote.ui.activity.tool;

import android.view.View;

import com.android.base.SharedPreferencesUtil;
import com.android.base.ui.XActivity;
import com.android.superli.btremote.R;
import com.gyf.immersionbar.ImmersionBar;

public class ControllerActivity extends XActivity implements View.OnClickListener {
    @Override
    public int getLayoutId() {
        return R.layout.activity_shoubing;
    }

    @Override
    public int getActivityTitle() {
        return R.string.device_edit;
    }

    @Override
    public void bindUI(View rootView) {
        super.bindUI(rootView);
        int theme = (int) SharedPreferencesUtil.getData("theme", 0);

        ImmersionBar.with(this)
                .titleBar(R.id.llt_content)
                .statusBarDarkFont(theme == 0, 0.2f)
                .keyboardEnable(true)
                .init();



    }

    @Override
    public void initData() {

    }


    @Override
    public void onClick(View v) {

    }
}
