package com.ruking.frame.sdk_demo.picturedemo;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.photolibrary.activity.PictureSelectionModular;
import com.ruking.frame.library.base.RKBaseActivity;
import com.ruking.frame.library.base.RKTransitionMode;
import com.ruking.frame.sdk_demo.R;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhy.autolayout.AutoFrameLayout;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Ruking.Cheng
 * @descrilbe TODO
 * @email 495095492@qq.com
 * @tel 18075121944
 * @date on 2018/5/14 上午10:37
 */
public class PictureActivity extends RKBaseActivity {

    @BindView(R.id.addlyout)
    AutoFrameLayout addlyout;
    private PictureSelectionModular pictureSelectionModular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        setSlidr();
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) { // Always true pre-M
                        pictureSelectionModular = new PictureSelectionModular(activity, addlyout,
                                getIntent());
                    } else {
                        // Oups permission denied
                        finish();
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (pictureSelectionModular != null)
            pictureSelectionModular.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pictureSelectionModular != null)
            pictureSelectionModular.onDestroy();
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
        return RKTransitionMode.RIGHT;
    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        onBackPressed();
    }
}
