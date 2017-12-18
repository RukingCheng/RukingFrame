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

    public void setTitle(CharSequence title) {
        this.title = title;
    }

    public CharSequence getDescription() {
        return description;
    }

    public void setDescription(CharSequence description) {
        this.description = description;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
