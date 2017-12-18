package com.ruking.frame.library.bean;

/**
 * @author Ruking.Cheng
 * @descrilbe
 * @email 495095492@qq.com
 * @tel 18075121944
 * @date on 2017/12/14 15:44
 */
public class Choice {
    private CharSequence title;
    private CharSequence description;
    private boolean checked;
    private Object tag;
    private boolean isClickable = true;

    public Choice() {
    }

    public Choice(CharSequence title) {
        this.title = title;
    }

    public Choice(CharSequence title, boolean checked) {
        this.title = title;
        this.checked = checked;
    }

    public Choice(CharSequence title, CharSequence description) {
        this.title = title;
        this.description = description;
    }

    public Choice(CharSequence title, CharSequence description, boolean checked) {
        this.title = title;
        this.description = description;
        this.checked = checked;
    }

    public CharSequence getTitle() {
        return title;
    }

    public Choice setTitle(CharSequence title) {
        this.title = title;
        return this;
    }

    public CharSequence getDescription() {
        return description;
    }

    public Choice setDescription(CharSequence description) {
        this.description = description;
        return this;
    }

    public boolean isChecked() {
        return checked;
    }

    public Choice setChecked(boolean checked) {
        this.checked = checked;
        return this;
    }

    public Object getTag() {
        return tag;
    }

    public Choice setTag(Object tag) {
        this.tag = tag;
        return this;
    }

    public boolean isClickable() {
        return isClickable;
    }

    public Choice setClickable(boolean clickable) {
        isClickable = clickable;
        return this;
    }
}
