package com.zhy.autolayout.utils;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.zhy.autolayout.AutoLayoutInfo;
import com.zhy.autolayout.R;
import com.zhy.autolayout.attr.Attrs;
import com.zhy.autolayout.attr.AutoAttr;
import com.zhy.autolayout.config.AutoLayoutConifg;

public class AutoUtils {

    /**
     * 会直接将view的LayoutParams上设置的width，height直接进行百分比处理
     *
     * @param view
     */
    public static void auto(View view) {
        autoSize(view);
        autoPadding(view);
        autoMargin(view);
        autoTextSize(view, AutoAttr.BASE_DEFAULT);
    }

    /**
     * @param view
     * @param attrs #Attrs.WIDTH|Attrs.HEIGHT
     * @param base  AutoAttr.BASE_WIDTH|AutoAttr.BASE_HEIGHT|AutoAttr.BASE_DEFAULT
     */
    public static void auto(View view, int attrs, int base) {
        AutoLayoutInfo autoLayoutInfo = AutoLayoutInfo.getAttrFromView(view, attrs, base);
        if (autoLayoutInfo != null)
            autoLayoutInfo.fillAttrs(view);
    }

    public static void autoTextSize(View view) {
        auto(view, Attrs.TEXTSIZE, AutoAttr.BASE_DEFAULT);
    }

    public static void autoTextSize(View view, int base) {
        auto(view, Attrs.TEXTSIZE, base);
    }

    public static void autoMargin(View view) {
        auto(view, Attrs.MARGIN, AutoAttr.BASE_DEFAULT);
    }

    public static void autoMargin(View view, int base) {
        auto(view, Attrs.MARGIN, base);
    }

    public static void autoPadding(View view) {
        auto(view, Attrs.PADDING, AutoAttr.BASE_DEFAULT);
    }

    public static void autoPadding(View view, int base) {
        auto(view, Attrs.PADDING, base);
    }

    public static void autoSize(View view) {
        auto(view, Attrs.WIDTH | Attrs.HEIGHT, AutoAttr.BASE_DEFAULT);
    }

    public static void autoSize(View view, int base) {
        auto(view, Attrs.WIDTH | Attrs.HEIGHT, base);
    }

    public static boolean autoed(View view) {
        Object tag = view.getTag(R.id.id_tag_autolayout_size);
        if (tag != null) return true;
        view.setTag(R.id.id_tag_autolayout_size, "Just Identify");
        return false;
    }

    public static float getPercentWidth1px() {
        int screenWidth = AutoLayoutConifg.getInstance().getScreenWidth();
        int designWidth = AutoLayoutConifg.getInstance().getDesignWidth();
        return 1.0f * screenWidth / designWidth;
    }

    public static float getPercentHeight1px() {
        int screenHeight = AutoLayoutConifg.getInstance().getScreenHeight();
        int designHeight = AutoLayoutConifg.getInstance().getDesignHeight();
        return 1.0f * screenHeight / designHeight;
    }


    public static int getPercentWidthSize(int val) {
        int screenWidth = AutoLayoutConifg.getInstance().getScreenWidth();
        int designWidth = AutoLayoutConifg.getInstance().getDesignWidth();
        return (int) (val * 1.0f / designWidth * screenWidth);
    }


    public static int getPercentWidthSizeBigger(int val) {
        int screenWidth = AutoLayoutConifg.getInstance().getScreenWidth();
        int designWidth = AutoLayoutConifg.getInstance().getDesignWidth();

        int res = val * screenWidth;
        if (res % designWidth == 0) {
            return res / designWidth;
        } else {
            return res / designWidth + 1;
        }

    }

    public static int getPercentHeightSizeBigger(int val) {
        int screenHeight = AutoLayoutConifg.getInstance().getScreenHeight();
        int designHeight = AutoLayoutConifg.getInstance().getDesignHeight();

        int res = val * screenHeight;
        if (res % designHeight == 0) {
            return res / designHeight;
        } else {
            return res / designHeight + 1;
        }
    }

    public static int getPercentHeightSize(int val) {
        int screenHeight = AutoLayoutConifg.getInstance().getScreenHeight();
        int designHeight = AutoLayoutConifg.getInstance().getDesignHeight();

        return (int) (val * 1.0f / designHeight * screenHeight);
    }

    public static void setTextViewDrawableSize(@NonNull TextView textView, AttributeSet attrs) {
        TypedArray a = textView.getContext().obtainStyledAttributes(attrs, R.styleable.AutoTextView);
        int iconHeight = a.getDimensionPixelOffset(R.styleable.AutoTextView_iconSize, 0);
        TypedValue val = a.peekValue(R.styleable.AutoTextView_iconSize);
        if (DimenUtils.isPxVal(val)) {
            iconHeight = AutoUtils.getPercentWidthSizeBigger(iconHeight);
        }
        Drawable[] drawables = textView.getCompoundDrawables();
        for (Drawable drawable : drawables) {
            if (drawable != null) {
                int iconWidth = iconHeight * drawable.getIntrinsicWidth() * drawable.getIntrinsicHeight();
                drawable.setBounds(0, 0, iconWidth, iconHeight);
            }
        }
        textView.setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
        a.recycle();
    }

}
