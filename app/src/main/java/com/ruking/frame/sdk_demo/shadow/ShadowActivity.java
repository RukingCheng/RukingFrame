package com.ruking.frame.sdk_demo.shadow;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.ruking.frame.library.base.RKBaseActivity;
import com.ruking.frame.library.base.RKTransitionMode;
import com.ruking.frame.sdk_demo.R;

import butterknife.ButterKnife;

/**
 * @author Ruking.Cheng
 * @descrilbe TODO
 * @email 495095492@qq.com
 * @tel 18075121944
 * @date on 2018/8/6 下午4:25
 */
public class ShadowActivity extends RKBaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shadow);
        ButterKnife.bind(this);
        setSlidr();

    }

    @Override
    public boolean isBindEventBusHere() {
        return false;
    }

    @Override
    public int getStatusBarColor() {
        return ContextCompat.getColor(activity, R.color.colorPrimaryDark);
    }

    @Override
    public int getStatusBarPlaceColor() {
        return 0;
    }

    @Override
    public boolean isShowStatusBarPlaceColor() {
        return false;
    }

    @Override
    public boolean isWindowSetting() {
        return false;
    }

    @Override
    public boolean toggleOverridePendingTransition() {
        return true;
    }

    @Override
    public RKTransitionMode getOverridePendingTransitionMode() {
        return RKTransitionMode.RIGHT;
    }
}
