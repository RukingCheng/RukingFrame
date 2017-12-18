package com.ruking.frame.library.widget;

import android.content.Context;
import android.support.annotation.DrawableRes;

import com.ruking.frame.library.R;
import com.ruking.frame.library.bean.Choice;
import com.ruking.frame.library.widget.adapter.ChoiceListAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Ruking.Cheng
 * @descrilbe 自定义列表
 * @email 495095492@qq.com
 * @tel 18075121944
 * @date on 2017/12/14 17:34
 */
public class RKDialogChoiceList {
    protected final Context context;
    private List<Choice> mChoices;
    private boolean isRadioIconHide = true;//是否显示Radio
    private RKDialogListener.OnSingleChoiceSelectListener onSingleChoiceSelectListener;
    private boolean isMultiselect;//是否是多选
    private RKDialogProfile profile;
    @DrawableRes
    private int imageIconChecked = R.drawable.rk_radio_checked;
    @DrawableRes
    private int imageIconNormal = R.drawable.rk_radio_normal;
    private Object tag;

    public RKDialogChoiceList(Context context) {
        this.context = context;
        mChoices = new ArrayList<>();
    }

    public Object getTag() {
        return tag;
    }

    public RKDialogChoiceList setTag(Object tag) {
        this.tag = tag;
        return this;
    }

    public RKDialogChoiceList setRadioIconHide(boolean radioIconHide) {
        isRadioIconHide = radioIconHide;
        return this;
    }

    public RKDialogChoiceList setOnSingleChoiceSelectListener(RKDialogListener.OnSingleChoiceSelectListener onSingleChoiceSelectListener) {
        this.onSingleChoiceSelectListener = onSingleChoiceSelectListener;
        return this;
    }

    public RKDialogChoiceList setImageIconChecked(@DrawableRes int imageIconChecked) {
        this.imageIconChecked = imageIconChecked;
        return this;
    }

    public RKDialogChoiceList setImageIconNormal(@DrawableRes int imageIconNormal) {
        this.imageIconNormal = imageIconNormal;
        return this;
    }

    public int getImageIconChecked() {
        return imageIconChecked;
    }

    public int getImageIconNormal() {
        return imageIconNormal;
    }

    public boolean isMultiselect() {
        return isMultiselect;
    }

    public RKDialogChoiceList setMultiselect(boolean multiselect) {
        isMultiselect = multiselect;
        if (multiselect) {
            if (imageIconChecked == R.drawable.rk_radio_checked)
                imageIconChecked = R.drawable.rk_choose_normal;
            if (imageIconNormal == R.drawable.rk_radio_normal)
                imageIconNormal = R.drawable.rk_choose_checked;
        }
        return this;
    }

    public RKDialogProfile getProfile() {
        return profile;
    }

    public RKDialogChoiceList setProfile(RKDialogProfile profile) {
        this.profile = profile;
        return this;
    }

    public RKDialogListener.OnSingleChoiceSelectListener getOnSingleChoiceSelectListener() {
        return onSingleChoiceSelectListener;
    }

    public List<Choice> getChoices() {
        return mChoices;
    }

    public boolean isRadioIconHide() {
        return isRadioIconHide;
    }

    public RKDialogChoiceList setChoices(CharSequence... choices) {
        mChoices.clear();
        for (CharSequence sequence : choices) {
            Choice choice = new Choice();
            choice.setTitle(sequence);
            mChoices.add(choice);
        }
        return this;
    }

    public RKDialogChoiceList setChoices(Choice... choices) {
        mChoices.clear();
        mChoices.addAll(Arrays.asList(choices));
        return this;
    }

    public RKDialogChoiceList setChoices(List<Choice> mChoices) {
        if (mChoices != null)
            this.mChoices = mChoices;
        return this;
    }

    // TODO
    public ChoiceListAdapter getAdapter(RKDialog dialog) {
        return new ChoiceListAdapter(dialog, this);
    }
}
