package com.ruking.frame.sdk_demo;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.ruking.frame.library.base.RKBaseBackActivity;
import com.ruking.frame.library.base.RKTransitionMode;

/**
 * @author Ruking.Cheng
 * @descrilbe TODO
 * @email 495095492@qq.com
 * @tel 18075121944
 * @date on 11/21 15:23
 */
public class TransitionModeActivity extends RKBaseBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transitionmode);
        String text = "FADE";
        switch (getIntent().getExtras().getInt("type")) {
            case 0:
                text = "LEFT";
                break;
            case 1:
                text = "TOP";
                break;
            case 2:
                text = "RIGHT";
                break;
            case 3:
                text = "BOTTOM";
                break;
            case 4:
                text = "SCALE";
                break;
        }
        ((TextView) findViewById(R.id.textView2)).setText(text);
    }

    @Override
    public boolean isBindEventBusHere() {
        return false;
    }

    @Override
    public int getStatusBarColor() {
//        return Color.parseColor("#000");
        return ContextCompat.getColor(activity, R.color.color_9000);
    }

    @Override
    public int getStatusBarPlaceColor() {
        return ContextCompat.getColor(activity, R.color.colorPrimaryDark);
    }

    @Override
    public boolean isShowStatusBarPlaceColor() {
        return true;
    }

    @Override
    public boolean isWindowSetting() {
        return true;
    }

    @Override
    public boolean toggleOverridePendingTransition() {
        return true;
    }

    @Override
    public RKTransitionMode getOverridePendingTransitionMode() {
        switch (getIntent().getExtras().getInt("type")) {
            case 0:
                return RKTransitionMode.LEFT;
            case 1:
                return RKTransitionMode.TOP;
            case 2:
                return RKTransitionMode.RIGHT;
            case 3:
                return RKTransitionMode.BOTTOM;
            case 4:
                return RKTransitionMode.SCALE;
        }
        return RKTransitionMode.FADE;
    }
}
