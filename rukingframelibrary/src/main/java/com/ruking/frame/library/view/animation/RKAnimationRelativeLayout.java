package com.ruking.frame.library.view.animation;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.ruking.frame.library.view.animation.base.RKViewAnimationBase;
import com.zhy.autolayout.AutoRelativeLayout;


/**
 * 放大缩小RelativeLayout
 *
 * @author 史伟成 E-mail:495095492@qq.com 电话：18075121944
 * @version 创建时间：2014-5-7 下午6:08:35
 */
public class RKAnimationRelativeLayout extends AutoRelativeLayout {
    private RKViewAnimationBase mRKViewAnimationBase;

    public RKAnimationRelativeLayout(Context context) {
        super(context);
        init(null);
    }

    public RKAnimationRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public RKAnimationRelativeLayout(Context context, AttributeSet attrs,
                                     int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }


    private void init(AttributeSet attributeSet) {
        mRKViewAnimationBase = new RKViewAnimationBase(this, attributeSet);
    }

    @Override
    public void draw(Canvas canvas) {
        mRKViewAnimationBase.draw(canvas);
        super.draw(canvas);
        mRKViewAnimationBase.drawOff(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRKViewAnimationBase.onSizeChanged(w, h);
    }

    public RKViewAnimationBase getRKViewAnimationBase() {
        return mRKViewAnimationBase;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!mRKViewAnimationBase.isOnClickable())
            return false;
        mRKViewAnimationBase.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}