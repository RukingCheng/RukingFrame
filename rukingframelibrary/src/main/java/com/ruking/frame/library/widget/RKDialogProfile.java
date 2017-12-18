package com.ruking.frame.library.widget;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.ruking.frame.library.view.animation.base.RKViewAnimationBase;

/**
 * @author Ruking.Cheng
 * @descrilbe 颜色和字体大小配置
 * @email 495095492@qq.com
 * @tel 18075121944
 * @date on 12/11 10:33
 */
public class RKDialogProfile {
    public static final int TITLE_BACKGROUND = 0xada671;//抬头的设置
    public static final int MESSAGE_BACKGROUND = 0xada673;//中间消息体
    public static final int CUSTOM_BACKGROUND = 0xada674;//预留选择框
    public static final int BUTTON_BACKGROUND = 0xada676;//底部按钮
    public static final int DIALOG_BACKGROUND = 0xada677;//对话框背景
    public static final int CHECKBOX_BACKGROUND = 0xada678;//复选框背景
    private int type;
    private int backgroundColor = -1;//背景色
    private int backgroundColorRes = -1;//背景色
    private int backgroundResId = -1;//背景图片
    private int textColor = -1;//文字颜色
    private int textColorRes = -1;//文字颜色
    private int textSize = -1;//文字大小
    private int strokeColor = -1;//边框颜色
    private int strokeColorRes = -1;//边框颜色
    private int mStrokeWidth = -1;//边框宽度
    private int roundCornerTopLeft = -1;//圆角
    private int roundCornerTopRight = -1;//圆角
    private int roundCornerBottomLeft = -1;//圆角
    private int roundCornerBottomRight = -1;//圆角
    private int itmeColor = -1;//单选框或复选框颜色
    private int itmeColorRes = -1;//单选框或复选框颜色

    public RKDialogProfile() {
    }

    public RKDialogProfile(int type) {
        this.type = type;
    }

    public int getTextSize() {
        return textSize;
    }

    public RKDialogProfile setTextSize(int textSize) {
        this.textSize = textSize;
        return this;
    }

    public int getType() {
        return type;
    }

    public RKDialogProfile setType(int type) {
        this.type = type;
        return this;
    }

    public int getItmeColor() {
        return itmeColor;
    }

    public RKDialogProfile setItmeColor(@ColorInt int itmeColor) {
        this.itmeColor = itmeColor;
        return this;
    }

    public int getItmeColorRes() {
        return itmeColorRes;
    }

    public RKDialogProfile setItmeColorRes(@ColorRes int itmeColorRes) {
        this.itmeColorRes = itmeColorRes;
        return this;
    }

    public int getTextColor() {
        return textColor;
    }

    public RKDialogProfile setTextColor(@ColorInt int textColor) {
        this.textColor = textColor;
        return this;
    }

    public int getTextColorRes() {
        return textColorRes;
    }

    public RKDialogProfile setTextColorRes(@ColorRes int textColorRes) {
        this.textColorRes = textColorRes;
        return this;
    }

    public int getStrokeColor() {
        return strokeColor;
    }

    public RKDialogProfile setStrokeColor(@ColorInt int strokeColor) {
        this.strokeColor = strokeColor;
        return this;
    }

    public int getStrokeColorRes() {
        return strokeColorRes;
    }

    public RKDialogProfile setStrokeColorRes(@ColorRes int strokeColorRes) {
        this.strokeColorRes = strokeColorRes;
        return this;
    }

    public int getStrokeWidth() {
        return mStrokeWidth;
    }

    public RKDialogProfile setStrokeWidth(int mStrokeWidth) {
        this.mStrokeWidth = mStrokeWidth;
        return this;
    }

    public RKDialogProfile setBackgroundColor(@ColorInt int color) {
        backgroundColor = color;
        return this;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }


    public RKDialogProfile setBackgroundColorRes(@ColorRes int color) {
        backgroundColorRes = color;
        return this;
    }

    public int getBackgroundColorRes() {
        return backgroundColorRes;
    }

    public RKDialogProfile setBackgroundResource(int resid) {
        backgroundResId = resid;
        return this;
    }

    public int getBackgroundResource() {
        return backgroundResId;
    }


    public int getRoundCornerTopLeft() {
        return roundCornerTopLeft;
    }

    public RKDialogProfile setRoundCornerTopLeft(int roundCornerTopLeft) {
        this.roundCornerTopLeft = roundCornerTopLeft;
        return this;
    }

    public RKDialogProfile setRroundCorner(int roundCorner) {
        this.roundCornerTopLeft = roundCorner;
        this.roundCornerTopRight = roundCorner;
        this.roundCornerBottomLeft = roundCorner;
        this.roundCornerBottomRight = roundCorner;
        return this;
    }

    public float getRoundCornerTopRight() {
        return roundCornerTopRight;
    }

    public RKDialogProfile setRoundCornerTopRight(int roundCornerTopRight) {
        this.roundCornerTopRight = roundCornerTopRight;
        return this;
    }

    public float getRoundCornerBottomLeft() {
        return roundCornerBottomLeft;
    }

    public RKDialogProfile setRoundCornerBottomLeft(int roundCornerBottomLeft) {
        this.roundCornerBottomLeft = roundCornerBottomLeft;
        return this;
    }

    public float getRoundCornerBottomRight() {
        return roundCornerBottomRight;
    }

    public RKDialogProfile setRoundCornerBottomRight(int roundCornerBottomRight) {
        this.roundCornerBottomRight = roundCornerBottomRight;
        return this;
    }

    public void setBackgroundProfile(Context context, View view) {
        if (backgroundColor != -1) {
            view.setBackgroundColor(backgroundColor);
        } else if (backgroundColorRes != -1) {
            view.setBackgroundColor(ContextCompat.getColor(context, backgroundColorRes));
        }
        if (backgroundResId != -1) {
            view.setBackgroundResource(backgroundResId);
        }
    }

    public void setTextProfile(Context context, TextView view) {
        if (textColor != -1) {
            view.setTextColor(textColor);
        } else if (textColorRes != -1) {
            view.setTextColor(ContextCompat.getColor(context, textColorRes));
        }
        if (textSize != -1) {
            view.setTextSize(textSize);
        }
    }

    public void setRKViewProfile(Context context, RKViewAnimationBase base) {
        if (strokeColor != -1) {
            base.setStrokeColor(strokeColor);
        } else if (strokeColorRes != -1) {
            base.setStrokeColor(ContextCompat.getColor(context, strokeColorRes));
        }
        if (mStrokeWidth != -1) {
            base.setStrokeWidth(mStrokeWidth);
        }
        if (roundCornerTopLeft != -1) {
            base.setRoundCornerTopLeft(roundCornerTopLeft);
        }
        if (roundCornerTopRight != -1) {
            base.setRoundCornerTopRight(roundCornerTopRight);
        }
        if (roundCornerBottomLeft != -1) {
            base.setRoundCornerBottomLeft(roundCornerBottomLeft);
        }
        if (roundCornerBottomRight != -1) {
            base.setRoundCornerBottomRight(roundCornerBottomRight);
        }
    }
}
