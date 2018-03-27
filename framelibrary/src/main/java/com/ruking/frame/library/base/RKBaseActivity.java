package com.ruking.frame.library.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import com.ruking.frame.library.R;
import com.ruking.frame.library.utils.RKWindowUtil;
import com.zhy.autolayout.AutoLayoutActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

/**
 * @author Ruking.Cheng
 * @descrilbe TODO
 * @email 495095492@qq.com
 * @tel 18075121944
 * @date on 11/21 14:34
 */
public abstract class RKBaseActivity extends AutoLayoutActivity {
    /**
     * context
     */
    protected Activity activity = null;
    protected View viewStatusBarPlace;
    protected FrameLayout frameLayoutContentPlace;


    @TargetApi(Build.VERSION_CODES.ECLAIR)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (toggleOverridePendingTransition()) {
            switch (getOverridePendingTransitionMode()) {
                case LEFT:
                    overridePendingTransition(R.anim.left_in, R.anim.left_out);
                    break;
                case RIGHT:
                    overridePendingTransition(R.anim.right_in, R.anim.right_out);
                    break;
                case TOP:
                    overridePendingTransition(R.anim.top_in, R.anim.top_out);
                    break;
                case BOTTOM:
                    overridePendingTransition(R.anim.bottom_in, R.anim.bottom_out);
                    break;
                case SCALE:
                    overridePendingTransition(R.anim.scale_in, R.anim.scale_out);
                    break;
                case FADE:
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    break;
            }
        }
        if (isWindowSetting())
            RKWindowUtil.windowSetting(this);
        super.onCreate(savedInstanceState);
        // 将系统自带的标题框隐藏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity = this;
        super.setContentView(R.layout.rk_activity_compat_status_bar);
        if (getStatusBarColor() != 0) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    getWindow().setStatusBarColor(getStatusBarColor());
                    //底部导航栏
                }
            } catch (Exception ignored) {
            }
        }
        viewStatusBarPlace = findViewById(R.id.view_status_bar_place);
        frameLayoutContentPlace = (FrameLayout) findViewById(R.id.frame_layout_content_place);
        if (isShowStatusBarPlaceColor()) {
            ViewGroup.LayoutParams params = viewStatusBarPlace.getLayoutParams();
            params.height = RKWindowUtil.getStatusBarHeight(this);
            viewStatusBarPlace.setLayoutParams(params);
            viewStatusBarPlace.setVisibility(View.VISIBLE);
            if (getStatusBarPlaceColor() != 0)
                viewStatusBarPlace.setBackgroundColor(getStatusBarPlaceColor());
        } else {
            viewStatusBarPlace.setVisibility(View.GONE);
        }
        if (isBindEventBusHere()) {
            EventBus.getDefault().register(this);
        }
        RKAppManager.getAppManager().addActivity(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setContentView(int layoutResID) {
        View contentView = LayoutInflater.from(this).inflate(layoutResID, null);
        frameLayoutContentPlace.addView(contentView);
        ButterKnife.bind(this);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @TargetApi(Build.VERSION_CODES.ECLAIR)
    @Override
    public void finish() {
        super.finish();
        RKAppManager.getAppManager().removeActivity(this);
        if (toggleOverridePendingTransition()) {
            switch (getOverridePendingTransitionMode()) {
                case LEFT:
                    overridePendingTransition(R.anim.left_in, R.anim.left_out);
                    break;
                case RIGHT:
                    overridePendingTransition(R.anim.right_in, R.anim.right_out);
                    break;
                case TOP:
                    overridePendingTransition(R.anim.top_in, R.anim.top_out);
                    break;
                case BOTTOM:
                    overridePendingTransition(R.anim.bottom_in, R.anim.bottom_out);
                    break;
                case SCALE:
                    overridePendingTransition(R.anim.scale_in, R.anim.scale_out);
                    break;
                case FADE:
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isBindEventBusHere()) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            // 如果打开了软键盘 就关闭它
            View view = getWindow().peekDecorView();
            if (view != null) {
                InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception ignored) {
        }
    }


    /**
     * 是否打开EventBus
     */
    public abstract boolean isBindEventBusHere();


    /**
     * 状态栏颜色
     */
    @ColorInt
    public abstract int getStatusBarColor();

    /**
     * 嵌入式状态栏颜色
     *
     * @return
     */
    @ColorInt
    public abstract int getStatusBarPlaceColor();

    /**
     * 是否显示嵌入式状态栏
     *
     * @return
     */
    public abstract boolean isShowStatusBarPlaceColor();

    /**
     * 是否状态栏嵌入
     *
     * @return
     */
    public abstract boolean isWindowSetting();

    /**
     * toggle overridePendingTransition
     */
    public abstract boolean toggleOverridePendingTransition();

    /**
     * get the overridePendingTransition mode
     */
    public abstract RKTransitionMode getOverridePendingTransitionMode();


    /**
     * startActivity
     */
    protected void readyGo(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * startActivity with bundle
     */
    protected void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * startActivity then finish
     */
    protected void readyGoThenKill(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        finish();
    }

    /**
     * startActivity with bundle then finish
     */
    protected void readyGoThenKill(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        finish();
    }

    /**
     * startActivityForResult
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, requestCode);
    }

    /**
     * startActivityForResult with bundle
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }


}
