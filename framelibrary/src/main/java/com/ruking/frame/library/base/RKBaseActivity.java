package com.ruking.frame.library.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;
import com.ruking.frame.library.R;
import com.ruking.frame.library.rxbus.RxBus;
import com.ruking.frame.library.utils.RKWindowUtil;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;

/**
 * @author Ruking.Cheng
 * @descrilbe RK框架中的
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
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
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
        frameLayoutContentPlace = findViewById(R.id.frame_layout_content_place);
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
        if (isRxBusHere()) {
            RxBus.getDefault().register(this);
        }
        RKAppManager.getAppManager().addActivity(this);
    }

    protected void setSlidr() {
        int primary = -1;
        int secondary = -1;
        if (getStatusBarColor() != 0) {
            primary = getStatusBarColor();
            secondary = getStatusBarColor();
        }
        /*
        primaryColor : 滑动时状态栏渐变结束的颜色
        secondaryColor : 滑动时状态栏渐变开始的颜色
        position : 设置滑动时起始方向,可以同时设置多个,比如设置left,意思是从左向右滑
        sensitivity : 响应的敏感度,0-1f,默认值是1f
        scrimColor : 滑动时acitvity之间的蒙层颜色,默认是黑色
        scrimStartAlpha : 滑动开始时Activity之间蒙层颜色的透明度,0-1f,默认值0.8f
        scrimEndAlpha : 滑动结束时Activity之间蒙层颜色的透明度,0-1f,默认值0f
        velocityThreshold : 滑动时移动速度阈值,超过这个值会响应滑动事件
        distanceThreshold : 滑动时手指移动距离占屏幕百分比的阈值,超过这个值才响应事件
        edge : boolean类型,是否设置响应事件的边界,默认是false,没有边界,滑动任何地方都有响应
        edgeSize : 边界的大小占屏幕的百分比,0-1f ,这时要看positon的方向,比如position是left,edgeSize是0.2f,意思就是边界的大小等于距离屏幕左边界占屏幕20%的大小
         */
        SlidrConfig.Builder mBuilder = new SlidrConfig.Builder()
                .primaryColor(primary)//滑动时状态栏的渐变结束的颜色
                .secondaryColor(secondary)//滑动时状态栏的渐变开始的颜色
                .scrimColor(Color.BLACK)//滑动时Activity之间的颜色
                .position(getSlidrPosition())//从左边滑动
                .scrimStartAlpha(0f)//滑动开始时两个Activity之间的透明度
                .scrimEndAlpha(0f)//滑动结束时两个Activity之间的透明度
                .velocityThreshold(5f)//超过这个滑动速度，忽略位移限定值就切换Activity
                .edge(true)//boolean类型,是否设置响应事件的边界,默认是false,没有边界,滑动任何地方都有响应
                .edgeSize(0.18f)//边界的大小占屏幕的百分比,0-1f ,这时要看positon的方向,比如position是left,edgeSize是0.2f,意思就是边界的大小等于距离屏幕左边界占屏幕20%的大小
                .distanceThreshold(.35f);//滑动位移占屏幕的百分比，超过这个间距就切换Activity
        SlidrConfig mSlidrConfig = mBuilder.build();
        Slidr.attach(this, mSlidrConfig);
    }

    protected SlidrPosition getSlidrPosition() {
        if (toggleOverridePendingTransition()) {
            switch (getOverridePendingTransitionMode()) {
                case LEFT:
                    return SlidrPosition.RIGHT;
                case RIGHT:
                    return SlidrPosition.LEFT;
                case TOP:
                    return SlidrPosition.BOTTOM;
                case BOTTOM:
                    return SlidrPosition.TOP;
                case SCALE:
                    return SlidrPosition.BOTTOM;
            }
        }
        return SlidrPosition.HORIZONTAL;
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
        if (isRxBusHere()) {
            RxBus.getDefault().unRegister(this);
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
     * 是否打开RxBus
     */
    public abstract boolean isRxBusHere();


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
