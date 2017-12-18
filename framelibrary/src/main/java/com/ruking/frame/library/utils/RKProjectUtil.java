package com.ruking.frame.library.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 项目中一些判断和获取
 *
 * @author 史伟成 E-mail:495095492@qq.com 电话：18075121944
 * @time 创建时间：2017/5/6 15:02
 */

public class RKProjectUtil {
    /**
     * 如果退回true，则为电话号码，false反之
     *
     * @param phonenumber
     * @return
     */
    public boolean check(String phonenumber) {
        String phone = "0\\d{2,3}\\d{7,8}";
        Pattern p = Pattern.compile(phone);
        Matcher m = p.matcher(phonenumber);
        return m.matches();
    }

    /**
     * 如果退回true，则为手机号码，false反之
     *
     * @param phoneNumber
     * @return
     */
    public boolean checkPhoneNumber(String phoneNumber) {
        // String phone =
        // "((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(17[0-9]{1})|(14[0-9]{1})){1}\\d{8}";
        String phone = "(1[0-9]{2}){1}\\d{8}";
        Pattern p = Pattern.compile(phone);
        Matcher m = p.matcher(phoneNumber);
        return m.matches();
    }

    /**
     * 判断邮箱格式,如果退回true，则为邮箱，false反之
     *
     * @param email
     * @return
     * @author 史伟成 E-mail:495095492@qq.com 电话：15216801944
     * @version 创建时间：2014-1-13 下午6:51:45
     */
    public boolean checkEmail(String email) {
        Pattern pattern = Pattern.compile(
                "^[a-zA-Z0-9]*[\\w\\.-]*[a-zA-Z0-9]*@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * 判断QQ号格式,如果退回true，则为QQ号，false反之
     *
     * @param qq
     * @return
     * @author 史伟成 E-mail:495095492@qq.com 电话：15216801944
     * @version 创建时间：2015-2-2 上午11:14:33
     */
    public boolean checkQQ(String qq) {
        Pattern pattern = Pattern.compile("[1-9][0-9]{5,9}");
        Matcher matcher = pattern.matcher(qq);
        return matcher.matches();
    }

    /**
     * 判断SD是否可读写
     *
     * @return True if the external storage is writable. False otherwise.
     * @author 史伟成 E-mail:495095492@qq.com 电话：15216801944
     * @version 创建时间：2015-8-4 下午5:31:37
     */
    public boolean isWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;

    }

    /**
     * 判断SD卡是否可用
     *
     * @return True if the external storage is available. False otherwise.
     * @author 史伟成 E-mail:495095492@qq.com 电话：15216801944
     * @version 创建时间：2015-8-4 下午5:31:05
     */
    public boolean isAvailable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)
                || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    /**
     * Android API自带的方法获取SD卡路径
     *
     * @return
     * @author 史伟成 E-mail:495095492@qq.com 电话：18075121944
     * @time 创建时间：2017/5/6 下午3:13
     */
    public String getSdCardPath() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    /**
     * 读取资源文件String
     *
     * @param fileName
     * @param context
     * @return
     * @author 史伟成 E-mail:495095492@qq.com 电话：15216801944
     * @version 创建时间：2015-7-9 上午10:48:47
     */
    public String getFromAssets(String fileName, Context context) {
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName),
                    "GBK");
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null)
                Result += line + "\n";
            return Result;
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * 判断App是否安装方法一
     *
     * @param context
     * @param uri
     * @return
     * @author 史伟成 E-mail:495095492@qq.com 电话：15216801944
     * @version 创建时间：2015-5-18 上午9:08:50
     */
    public boolean isAppInstalled(Context context, String uri) {
        PackageManager pm = context.getPackageManager();
        boolean installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            installed = false;
        }
        return installed;
    }

    /**
     * 判断App是否安装方法二
     *
     * @param context
     * @param packageName
     * @return
     * @author 史伟成 E-mail:495095492@qq.com 电话：15216801944
     * @version 创建时间：2015-5-18 上午9:08:50
     */
    public boolean isAppInstalledTwo(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        List<String> pName = new ArrayList<String>();
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName.contains(packageName);
    }
}
