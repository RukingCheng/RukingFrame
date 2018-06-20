package com.ruking.frame.library.view.animation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;

/**
 * 有弹性的ScrollView+阻尼
 *
 * @author 史伟成 E-mail:495095492@qq.com 电话：15216801944
 * @version 创建时间：2015-3-27 下午3:50:10
 */
public class RKElasticScrollView extends android.support.v4.widget.NestedScrollView {
    // 拖动的距离 size = 4 的意思 只允许拖动屏幕的1/4
    private static final int size = 4;
    private View inner;
    private float y;
    private Rect normal = new Rect();
    private boolean DOWN = true;

    public RKElasticScrollView(Context context) {
        super(context);
    }

    public RKElasticScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onFinishInflate() {
        if (getChildCount() > 0) {
            inner = getChildAt(0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (inner == null) {
            return super.onTouchEvent(ev);
        } else {
            commOnTouchEvent(ev);
        }
        return super.onTouchEvent(ev);
    }

    public void commOnTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            // case MotionEvent.ACTION_DOWN:
            // y = ev.getY();
            // break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (!normal.isEmpty()) {
                    // 开启移动动画
                    TranslateAnimation ta = new TranslateAnimation(0, 0,
                            inner.getTop(), normal.top);
                    ta.setDuration(200);
                    inner.startAnimation(ta);
                    // 设置回到正常的布局位置
                    inner.layout(normal.left, normal.top, normal.right,
                            normal.bottom);
                    normal.setEmpty();
                }
                DOWN = true;
                break;
            case MotionEvent.ACTION_MOVE:
                if (DOWN) {
                    y = ev.getY();
                    DOWN = false;
                }
                final float preY = y;
                float nowY = ev.getY();
                //size=4 表示 拖动的距离为屏幕的高度的1/4
                int deltaY = (int) (preY - nowY) / size;
                // 滚动
                // scrollBy(0, deltaY);
                y = nowY;
                // 当滚动到最上或者最下时就不会再滚动，这时移动布局
                if (isNeedMove()) {
                    if (normal.isEmpty()) {
                        // 保存正常的布局位置
                        normal.set(inner.getLeft(), inner.getTop(),
                                inner.getRight(), inner.getBottom());
                        return;
                    }
                    int yy = inner.getTop() - deltaY;
                    // 移动布局
                    inner.layout(inner.getLeft(), yy, inner.getRight(),
                            inner.getBottom() - deltaY);
                }
                break;
            default:
                break;
        }
    }

    // 是否需要移动布局
    public boolean isNeedMove() {
        int offset = inner.getMeasuredHeight() - getHeight();
        int scrollY = getScrollY();
        return scrollY == 0 || scrollY == offset;
    }

}