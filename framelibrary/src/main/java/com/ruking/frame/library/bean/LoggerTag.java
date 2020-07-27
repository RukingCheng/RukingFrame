package com.ruking.frame.library.bean;

/**
 * @author Ruking.Cheng
 * @descrilbe Logger的Tag对象
 * @email 495095492@qq.com
 * @tel 18075121944
 * @date on 2020-07-27 17:25
 */
public class LoggerTag {
    private int priority;
    private String tag;
    private String msg;

    public LoggerTag(int priority, String tag, String msg) {
        this.priority = priority;
        this.tag = tag;
        this.msg = msg;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
