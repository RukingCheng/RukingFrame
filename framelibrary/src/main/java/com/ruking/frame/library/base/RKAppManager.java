package com.ruking.frame.library.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * activity 管理栈
 *
 * @author 史伟成 E-mail:495095492@qq.com 电话：15216801944
 * @version 创建时间：2014-12-22 下午4:29:45
 */
public class RKAppManager {
    private static Stack<Activity> activityStack;
    private static RKAppManager instance;

    private RKAppManager() {
    }

    /**
     * 单一实例
     */
    public static RKAppManager getAppManager() {
        if (instance == null) {
            instance = new RKAppManager();
        }
        return instance;
    }

    public Stack<Activity> getActivityStack() {
        return activityStack;
    }

    /**
     * 添加Activity到堆栈
     */
    public synchronized void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    public synchronized void removeActivity(Activity activity) {
        if (activityStack.contains(activity)) {
            activityStack.remove(activity);
        }
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        return activityStack.lastElement();
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }


    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    public boolean isActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                return true;
            }
        }
        return false;
    }

    public boolean isActivity(Activity activity) {
        if (activity != null) {
            for (Activity activity1 : activityStack) {
                if (activity1.getClass().equals(activity.getClass())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        finishAllActivity(null);
    }

    public void finishAllActivity(Class<?> cls) {
        if (activityStack != null) {
            List<Activity> integers = new ArrayList<>();
            for (int i = 0; i < RKAppManager.getAppManager().getActivityStack().size(); i++) {
                Activity activity = RKAppManager.getAppManager()
                        .getActivityStack().get(i);
                if (activity != null && (cls == null || !activity.getClass().equals(cls))) {
                    integers.add(activity);
                }
            }
            for (Activity integer : integers) {
                finishActivity(integer);
            }
            if (cls == null) {
                activityStack.clear();
                activityStack = null;
            }
        }
    }

    /**
     * 退出应用程序
     */
    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    public void appExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception ignored) {
        }
    }
}
