package com.ruking.frame.sdk_demo;

import android.app.Application;

import com.ruking.frame.library.base.RKCrashHandler;
import com.ruking.frame.library.utils.Logger;

import org.greenrobot.eventbus.EventBus;

public class CrashApplication extends Application {
    private static CrashApplication mInstance = null;

    public static CrashApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        EventBus.builder().addIndex(new MyEventBusIndex()).installDefaultEventBus();
        Logger.setLogEnable(true);
        RKCrashHandler.getInstance(this, MainActivity.class);
    }

}