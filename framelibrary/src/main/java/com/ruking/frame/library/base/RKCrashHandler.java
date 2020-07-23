package com.ruking.frame.library.base;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.ruking.frame.library.utils.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;

/**
 * UncaughtException处理类,当程序发生Uncaught异常的时候,有该类来接管程序,并记录发送错误报告.
 *
 * @author 史伟成 E-mail:495095492@qq.com 电话：15216801944
 * @version 创建时间：2014-3-31 上午10:56:37
 */
@SuppressLint({"ShowToast", "SdCardPath"})
public class RKCrashHandler implements UncaughtExceptionHandler {
    // 系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private static RKCrashHandler INSTANCE = new RKCrashHandler();
    private UncaughtException exception;

    /**
     * 保证只有一个CrashHandler实例
     */
    private RKCrashHandler() {
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     *
     * @return
     * @author 史伟成 E-mail:495095492@qq.com 电话：15216801944
     * @version 创建时间：2014-3-31 下午12:27:18
     */
    public static RKCrashHandler getInstance() {
        return INSTANCE;
    }

    /**
     * 直接设置重启
     *
     * @param context
     * @param c
     */
    public static void getInstance(Context context, Class<?> c) {
        RKCrashHandler crashHandler = getInstance();
        crashHandler.init(new UncaughtException() {
            private boolean isUn;// 手机root后会报些莫名其妙的错误,这里用它禁止一些错误导致程序重启

            @Override
            public void uncaughtException() {
                if (isUn) {
                    isUn = false;
                } else if (c != null) {
                    Intent intent = new Intent(context, c);
                    @SuppressLint("WrongConstant")
                    PendingIntent restartIntent = PendingIntent.getActivity(context, 0, intent,
                            Intent.FLAG_ACTIVITY_NEW_TASK);
                    // 退出程序
                    AlarmManager mgr = (AlarmManager) context.getSystemService(Context
                            .ALARM_SERVICE);
                    mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, restartIntent); // 1秒钟后重启应用
                }
                RKAppManager.getAppManager().appExit(context);
                // 退出程序
                android.os.Process.killProcess(android.os.Process.myPid());
                System.gc();
                System.runFinalization();
                System.exit(0);
            }

            @Override
            public void handleException(String ex) {
                if (ex != null && ex.contains("Unable to start service")) {
                    isUn = true;
                } else {
                    Logger.e("NetworkingService", ex);
                }
            }
        });
    }


    /**
     * 初始化
     *
     * @author 史伟成 E-mail:495095492@qq.com 电话：15216801944
     * @version 创建时间：2014-3-31 下午12:27:27
     */
    public void init(UncaughtException exception) {
        this.exception = exception;
        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            if (this.exception != null)
                this.exception.uncaughtException();
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     * @author 史伟成 E-mail:495095492@qq.com 电话：15216801944
     * @version 创建时间：2014-3-31 下午12:47:53
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        final StringBuilder sb = new StringBuilder();
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        if (this.exception != null)
            this.exception.handleException(sb.toString());
        return true;
    }

    public interface UncaughtException {
        void uncaughtException();

        void handleException(String ex);
    }
}