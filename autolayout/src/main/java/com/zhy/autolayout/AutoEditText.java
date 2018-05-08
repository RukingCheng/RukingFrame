package com.zhy.autolayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import com.zhy.autolayout.utils.AutoUtils;

@SuppressLint("AppCompatCustomView")
public class AutoEditText extends AppCompatEditText {

    public AutoEditText(Context context) {
        super(context);
        init(null);
    }

    public AutoEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public AutoEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        AutoUtils.setTextViewDrawableSize(this, attrs);
    }

}
