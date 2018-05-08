package com.zhy.autolayout;

import android.content.Context;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;

import com.zhy.autolayout.utils.AutoUtils;

public class AutoRadiuButton extends AppCompatRadioButton {
    public AutoRadiuButton(Context context) {
        super(context);
        init(null);
    }

    public AutoRadiuButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public AutoRadiuButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        AutoUtils.setTextViewDrawableSize(this, attrs);
    }
}
