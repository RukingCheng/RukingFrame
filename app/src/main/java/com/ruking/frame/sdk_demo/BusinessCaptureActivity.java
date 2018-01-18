package com.ruking.frame.sdk_demo;

import android.content.Intent;
import android.os.Bundle;

import com.acker.simplezxing.activity.CaptureActivity;
import com.google.zxing.Result;
import com.ruking.frame.library.base.RKTransitionMode;

/**
 * @author Ruking.Cheng
 * @descrilbe 统一扫一扫界面（业务实现只需要在Main里面配置即可）
 * @email 495095492@qq.com
 * @tel 18075121944
 * @date on 2017/7/11 17:37
 */

public class BusinessCaptureActivity extends CaptureActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void handleDecode(Result obj) {
        super.handleDecode(obj);
        final String data = recode(obj.getText().trim());
        Intent intent = new Intent();
        intent.putExtra("data", data == null ? "" : data);
        setResult(RESULT_OK, intent);
        onBackPressed();
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected int getStatusBarColor() {
        return 0;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return true;
    }

    @Override
    protected RKTransitionMode getOverridePendingTransitionMode() {
        return RKTransitionMode.FADE;
    }
}
