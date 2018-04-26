package com.zhy.autolayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.zhy.autolayout.utils.AutoUtils;
import com.zhy.autolayout.utils.DimenUtils;

/**
 * @package: com.zhuku.widget
 * @fileName: AutoTextView
 * @data: 2018/4/26 11:19
 * @author: ShiLiang
 * @describe:
 */
public class AutoTextView extends AppCompatTextView {

    public AutoTextView(Context context) {
        this(context, null);
    }

    public AutoTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AutoTextView);

        int iconWidth = a.getDimensionPixelOffset(R.styleable.AutoTextView_icon_width, 0);
        int iconHeight = a.getDimensionPixelOffset(R.styleable.AutoTextView_icon_height, 0);

        TypedValue val = a.peekValue(R.styleable.AutoTextView_icon_width);
        if (DimenUtils.isPxVal(val)) {
            iconWidth = AutoUtils.getPercentWidthSizeBigger(iconWidth);
        }
        TypedValue val1 = a.peekValue(R.styleable.AutoTextView_icon_height);
        if (DimenUtils.isPxVal(val1)) {
            iconHeight = AutoUtils.getPercentWidthSizeBigger(iconHeight);
        }

        Drawable[] drawables = getCompoundDrawables();
        for (Drawable drawable : drawables) {
            if (drawable != null) {
                drawable.setBounds(0, 0, iconWidth, iconHeight);
            }
        }
        setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
        a.recycle();
    }
}
