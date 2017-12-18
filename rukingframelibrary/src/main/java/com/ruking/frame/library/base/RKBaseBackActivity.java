package com.ruking.frame.library.base;

import android.graphics.Color;
import android.os.Bundle;

import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;

/**
 * @author Ruking.Cheng
 * @descrilbe 可以左右上下滑动关闭Activity
 * @email 495095492@qq.com
 * @tel 18075121944
 * @date on 11/21 15:58
 */
public abstract class RKBaseBackActivity extends RKBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int primary = -1;
        int secondary = -1;
        if (getStatusBarColor() != 0) {
            primary = getStatusBarColor();
            secondary = getStatusBarColor();
//            try {
//                primary = ContextCompat.getColor(activity, getStatusBarColor());
//                secondary = ContextCompat.getColor(activity, getStatusBarColor());
//            } catch (Exception ignored) {
//            }
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

    public SlidrPosition getSlidrPosition() {
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
}
