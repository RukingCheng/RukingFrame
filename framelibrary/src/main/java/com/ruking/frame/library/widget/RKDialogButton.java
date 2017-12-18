package com.ruking.frame.library.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruking.frame.library.R;
import com.ruking.frame.library.view.animation.RKAnimationLinearLayout;


/**
 * @author Ruking.Cheng
 * @descrilbe 对话框添加Button
 * @email 495095492@qq.com
 * @tel 18075121944
 * @date on 12/8 16:46
 */
public class RKDialogButton {
    protected final Context context;
    private RKAnimationLinearLayout button;
    private ImageView itemImag;
    private TextView itemText;
    private RKDialogProfile profile;
    private RKDialogListener.OnClickListener onClickListener;
    private RKDialogListener.OnLongClickListener onLongClickListener;
    private int height = 80;
    private int margins = 10;
    private Object tag;

    @SuppressLint("InflateParams")
    public RKDialogButton(@NonNull Context context) {
        this.context = context;
        button = (RKAnimationLinearLayout) LayoutInflater.from(context).inflate(R.layout.rk_dialog_button, null);
        itemImag = button.findViewById(R.id.item_imag);
        itemText = button.findViewById(R.id.item_text);
    }

    public ImageView getItemImag() {
        return itemImag;
    }

    public RKDialogButton setGravity(int gravity) {
        button.setGravity(gravity);
        return this;
    }

    public RKDialogButton setImageResource(@DrawableRes int resId) {
        itemImag.setVisibility(View.VISIBLE);
        itemImag.setImageResource(resId);
        return this;
    }

    public RKDialogButton setImageBitmap(Bitmap bm) {
        itemImag.setVisibility(View.VISIBLE);
        itemImag.setImageBitmap(bm);
        return this;
    }

    public RKDialogProfile getProfile() {
        return profile;
    }

    public RKDialogButton setProfile(RKDialogProfile profile) {
        this.profile = profile;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public RKDialogButton setHeight(int height) {
        this.height = height;
        return this;
    }

    public int getMargins() {
        return margins;
    }

    public RKDialogButton setMargins(int margins) {
        this.margins = margins;
        return this;
    }

    public RKDialogButton setText(@StringRes int text) {
        if (text == 0) {
            return this;
        }
        setText(this.context.getText(text));
        return this;
    }

    public RKDialogButton setText(@NonNull CharSequence text) {
        itemText.setText(text);
        return this;
    }

    public Object getTag() {
        return tag;
    }

    public RKDialogButton setTag(Object tag) {
        this.tag = tag;
        return this;
    }

    public RKDialogListener.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public RKDialogButton setOnClickListener(RKDialogListener.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        return this;
    }

    public RKDialogListener.OnLongClickListener getOnLongClickListener() {
        return onLongClickListener;
    }

    public RKDialogButton setOnLongClickListener(RKDialogListener.OnLongClickListener onLongClickListener) {
        this.onLongClickListener = onLongClickListener;
        return this;
    }

    public View getView() {
        if (profile != null) {
            profile.setBackgroundProfile(context, button);
            profile.setTextProfile(context, itemText);
            profile.setRKViewProfile(context, button.getRKViewAnimationBase());
        }
        return button;
    }

}
