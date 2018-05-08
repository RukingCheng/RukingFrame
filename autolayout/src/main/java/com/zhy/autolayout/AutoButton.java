package com.zhy.autolayout;

import android.content.Context;
import android.util.AttributeSet;

import com.zhy.autolayout.utils.AutoUtils;

public class AutoButton extends android.support.v7.widget.AppCompatButton {
    public AutoButton(Context context) {
        super(context);
        init(null);
    }

    public AutoButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public AutoButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        AutoUtils.setTextViewDrawableSize(this, attrs);
    }
}
