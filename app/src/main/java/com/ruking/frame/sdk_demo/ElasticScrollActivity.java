package com.ruking.frame.sdk_demo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ruking.frame.library.base.RKBaseBackActivity;
import com.ruking.frame.library.base.RKTransitionMode;
import com.ruking.frame.library.view.animation.RKAnimationButton;
import com.ruking.frame.library.view.animation.RKSinkingView;


/**
 * RKElasticScrollView功能演示
 *
 * @author 史伟成 E-mail:495095492@qq.com 电话：18075121944
 * @time 创建时间：2017/5/6 10:25
 */

public class ElasticScrollActivity extends RKBaseBackActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        windowSetting();
        setContentView(R.layout.activityelasticscroll);
        RKAnimationButton mRKAnimationButton = findViewById(R.id.back);
        mRKAnimationButton.getRKViewAnimationBase().setRoundCornerBottomLeft(60);
        mRKAnimationButton.getRKViewAnimationBase().setRoundAsCircle(false);
        mRKAnimationButton.getRKViewAnimationBase().setStrokeWidth(10);
        mRKAnimationButton.setOnClickListener(v -> onBackPressed());
        final RKSinkingView mSinkingView = findViewById(R.id.q25_view1_sinking);
//        mSinkingView.setBackgroundBitmap(R.mipmap.bg_tk_003);
        mSinkingView.setBottomText("正确率");
        mSinkingView.setPercentThread(80);
//        mSinkingView.setCompanyText("%");
        RKAnimationButton but01 = findViewById(R.id.but01);
        but01.setOnClickListener(v -> {
            mSinkingView.setBottomText("正确率");
            mSinkingView.setPercentThread(30);
        });
        RKAnimationButton but02 = findViewById(R.id.but02);
        but02.setOnClickListener(v -> {
            mSinkingView.setBottomText("错误率");
            mSinkingView.setPercentThread(70);
        });
    }


    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected int getStatusBarColor() {
        return Color.BLACK;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return true;
    }

    @Override
    protected RKTransitionMode getOverridePendingTransitionMode() {
        return RKTransitionMode.RIGHT;
    }
}
