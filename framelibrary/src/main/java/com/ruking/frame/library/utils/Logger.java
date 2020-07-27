package com.ruking.frame.library.utils;

import android.util.Log;

import com.ruking.frame.library.bean.LoggerTag;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ruking.Cheng
 * @descrilbe 打印log工具类
 * @email 495095492@qq.com
 * @tel 18075121944
 * @date on 2017/8/23 8:54
 */
public class Logger {

    public static int viewHeight = 0;
    //设为false关闭日志
    private static boolean LOG_ENABLE = false;
    //设为false关闭显示日志
    private static boolean LOG_ENABLE_VIEW = false;
    private static List<LoggerTag> loggerTags = new ArrayList<>();
    private static List<LoggerAdapter> adapters = new ArrayList<>();

    public static void addAdapter(LoggerAdapter adapter) {
        adapters.add(adapter);
        showList();
    }

    public static void removeAdapter(LoggerAdapter adapter) {
        adapters.remove(adapter);
        showList();
    }

    public static void clearAdapter() {
        adapters.clear();
    }

    private static void showList() {
        if (LOG_ENABLE_VIEW) {
            for (LoggerAdapter adapter : adapters) {
                adapter.showList(loggerTags);
            }
        }
    }

    public static void setLogEnable(boolean logEnable) {
        LOG_ENABLE = logEnable;
    }

    public static boolean isLogEnableView() {
        return LOG_ENABLE_VIEW;
    }

    private static void addLoggerTagList(int i, String tag, String msg) {
        if (LOG_ENABLE_VIEW) {
            if (loggerTags.size() > 100) {
                loggerTags.remove(0);
            }
            loggerTags.add(new LoggerTag(i, tag, msg));
            showList();
        }
    }

    public static void clearLoggerTagList() {
        loggerTags.clear();
        showList();
    }

    public static void setLogEnableView(boolean logEnableView) {
        LOG_ENABLE_VIEW = logEnableView;
    }

    public static void i(String tag, String msg) {
        if (LOG_ENABLE) {
            Log.i(tag, msg);
        }
        addLoggerTagList(2, tag, msg);
    }


    public static void v(String tag, String msg) {
        if (LOG_ENABLE) {
            Log.v(tag, msg);
        }
        addLoggerTagList(0, tag, msg);
    }

    public static void d(String tag, String msg, Exception e) {
        if (LOG_ENABLE) {
            Log.d(tag, msg);
        }
        addLoggerTagList(1, tag, msg);
    }

    public static void w(String tag, String msg) {
        if (LOG_ENABLE) {
            Log.w(tag, msg);
        }
        addLoggerTagList(3, tag, msg);
    }

    public static void e(String tag, String msg) {
        if (LOG_ENABLE) {
            Log.e(tag, msg);
        }
        addLoggerTagList(4, tag, msg);
    }

}
