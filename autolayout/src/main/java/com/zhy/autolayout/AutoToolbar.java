package com.zhy.autolayout;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;

import com.zhy.autolayout.utils.AutoLayoutHelper;

/**
 * @package: com.zhuku.widget
 * @fileName: AutoToolbar
 * @data: 2018/4/19 17:31
 * @author: ShiLiang
 * @describe:
 */
public class AutoToolbar extends Toolbar {
    private AutoLayoutHelper mHelper = new AutoLayoutHelper(this);

    public AutoToolbar(Context context) {
        super(context);
        init();
    }

    public AutoToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @TargetApi(11)
    public AutoToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setContentInsetsAbsolute(0, 0);
        setContentInsetsRelative(0, 0);
        setContentInsetStartWithNavigation(0);
        setContentInsetEndWithActions(0);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!this.isInEditMode()) {
            this.mHelper.adjustChildren();
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    public AutoToolbar.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new AutoToolbar.LayoutParams(this.getContext(), attrs);
    }

    public static class LayoutParams extends Toolbar.LayoutParams implements AutoLayoutHelper.AutoLayoutParams {
        private AutoLayoutInfo mAutoLayoutInfo;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            this.mAutoLayoutInfo = AutoLayoutHelper.getAutoLayoutInfo(c, attrs);
        }

        public AutoLayoutInfo getAutoLayoutInfo() {
            return this.mAutoLayoutInfo;
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }
    }
}
