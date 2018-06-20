package com.ruking.frame.library.rxbus;

/**
 * 消息类
 * @author wzg 2016/9/21
 */
public class RxBusMessage {
    private int code;
    private Object object;

    public RxBusMessage(int code, Object o) {
        this.code = code;
        this.object = o;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
