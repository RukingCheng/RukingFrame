package com.ruking.frame.library.utils;

import android.util.Log;

/**
 * @author Ruking.Cheng
 * @descrilbe 打印log工具类
 * @email 495095492@qq.com
 * @tel 18075121944
 * @date on 2017/8/23 8:54
 */
public class Logger {

    //设为false关闭日志
    private static boolean LOG_ENABLE = false;

    public static void setLogEnable(boolean logEnable) {
        LOG_ENABLE = logEnable;
    }

    public static void i(String tag, String msg) {
        if (LOG_ENABLE) {
            Log.i(tag, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (LOG_ENABLE) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg, Exception e) {
        if (LOG_ENABLE) {
            Log.d(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (LOG_ENABLE) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (LOG_ENABLE) {
            Log.e(tag, msg);
        }
    }

}
