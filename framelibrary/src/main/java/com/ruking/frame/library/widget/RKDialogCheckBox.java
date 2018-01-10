package com.ruking.frame.library.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruking.frame.library.R;

/**
 * @author Ruking.Cheng
 * @descrilbe 对话框添加Button
 * @email 495095492@qq.com
 * @tel 18075121944
 * @date on 12/8 16:46
 */
@SuppressLint("RestrictedApi")
public class RKDialogCheckBox {
    protected final Context context;
    private ImageView itemImag;
    private TextView itemText;
    private LinearLayout layoutView;
    private RKDialogProfile profile;
    private RKDialogListener.OnCheckedChangeListener onCheckedChangeListener;
    private int margins = 0;
    private Object tag;
    private boolean isChecked = false;
    @DrawableRes
    private int imageIconChecked = R.mipmap.rk_choose_normal;
    @DrawableRes
    private int imageIconNormal = R.mipmap.rk_choose_checked;
    private boolean isClickable = true;

    @SuppressLint("InflateParams")
    public RKDialogCheckBox(@NonNull Context context) {
        this.context = context;
        layoutView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.rk_dialog_checkbox, null);
        itemImag = layoutView.findViewById(R.id.item_imag);
        itemText = layoutView.findViewById(R.id.item_text);
    }

    public boolean isClickable() {
        return isClickable;
    }

    public RKDialogCheckBox setClickable(boolean clickable) {
        isClickable = clickable;
        return this;
    }

    public RKDialogCheckBox setGravity(int gravity) {
        layoutView.setGravity(gravity);
        return this;
    }

    public RKDialogProfile getProfile() {
        return profile;
    }

    public RKDialogCheckBox setProfile(RKDialogProfile profile) {
        this.profile = profile;
        return this;
    }

    public RKDialogCheckBox setText(@StringRes int text) {
        if (text == 0) {
            return this;
        }
        setText(this.context.getText(text));
        return this;
    }

    public RKDialogCheckBox setText(@NonNull CharSequence text) {
        itemText.setText(text);
        return this;
    }

    public int getMargins() {
        return margins;
    }

    public RKDialogCheckBox setMargins(int margins) {
        this.margins = margins;
        return this;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public RKDialogCheckBox setChecked(boolean checked) {
        isChecked = checked;
        showCheckedView();
        return this;
    }

    public RKDialogCheckBox setImageIconChecked(@DrawableRes int imageIconChecked) {
        this.imageIconChecked = imageIconChecked;
        return this;
    }

    public RKDialogCheckBox setImageIconNormal(@DrawableRes int imageIconNormal) {
        this.imageIconNormal = imageIconNormal;
        return this;
    }

    public Object getTag() {
        return tag;
    }

    public RKDialogCheckBox setTag(Object tag) {
        this.tag = tag;
        return this;
    }

    public RKDialogListener.OnCheckedChangeListener getOnCheckedChangeListener() {
        return onCheckedChangeListener;
    }

    public RKDialogCheckBox setOnCheckedChangeListener(RKDialogListener.OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
        return this;
    }

    private void showCheckedView() {
        if (isChecked) {
            itemImag.setImageResource(imageIconChecked);
        } else {
            itemImag.setImageResource(imageIconNormal);
        }
        if (profile != null) {
            if (profile.getItmeColor() != -1) {
                if (isChecked)
                    itemImag.setColorFilter(profile.getItmeColor());
                else
                    itemImag.setColorFilter(null);
            } else if (profile.getItmeColorRes() != -1) {
                if (isChecked)
                    itemImag.setColorFilter(ContextCompat.getColor(context, profile.getItmeColorRes()));
                else
                    itemImag.setColorFilter(null);
            }
        }
    }

    public View getView() {
        if (profile != null) {
            if (profile.getItmeColor() != -1) {
                itemImag.setImageResource(imageIconChecked);
                itemImag.setColorFilter(profile.getItmeColor());
            } else if (profile.getItmeColorRes() != -1) {
                itemImag.setImageResource(imageIconChecked);
                itemImag.setColorFilter(ContextCompat.getColor(context, profile.getItmeColorRes()));
            }
        }
        showCheckedView();
        if (profile != null) {
            profile.setBackgroundProfile(context, layoutView);
            profile.setTextProfile(context, itemText);
        }
        return layoutView;
    }

}
