package com.android.superli.btremote.config;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.android.superli.btremote.hid.HidConsts;
import com.android.superli.btremote.hid.HidUtils;

public class ActLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        ActivityTack.tack.addActivity(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        if (HidConsts.HidDevice != null) {
            HidUtils.reConnect(activity);
            HidConsts.HidDevice = HidUtils.HidDevice;
            HidConsts.BtDevice = HidUtils.BtDevice;
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        ActivityTack.tack.removeActivity(activity);
    }

}
