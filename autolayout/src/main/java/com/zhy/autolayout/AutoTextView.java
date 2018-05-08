package com.zhy.autolayout;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.zhy.autolayout.utils.AutoUtils;

public class AutoTextView extends AppCompatTextView {

    public AutoTextView(Context context) {
        this(context, null);
    }

    public AutoTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        AutoUtils.setTextViewDrawableSize(this, attrs);
    }
}
