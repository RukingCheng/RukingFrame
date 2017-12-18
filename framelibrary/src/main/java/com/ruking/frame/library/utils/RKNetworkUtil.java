package com.ruking.frame.library.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * 网络判断
 *
 * @author 史伟成 E-mail:495095492@qq.com 电话：18075121944
 * @time 创建时间：2017/5/6 15:04
 */

public class RKNetworkUtil {
    /**
     * 获取MAC地址
     *
     * @param context
     * @return
     * @author 史伟成 E-mail:495095492@qq.com 电话：15216801944
     * @version 创建时间：2015-7-9 上午10:45:53
     */
    public String getLocalMacAddress(Context context) {
        String mac = getMacAddress();
        if (mac != null)
            return mac;
        else {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            if (info != null && info.getMacAddress() != null)
                return info.getMacAddress();
        }
        return "";
    }

    /**
     * Get the STB MacAddress
     *
     * @return
     * @author 史伟成 E-mail:495095492@qq.com 电话：15216801944
     * @version 创建时间：2015-7-9 上午10:45:41
     */
    public String getMacAddress() {
        try {
            return loadFileAsString("/sys/class/net/eth0/address").toUpperCase().substring(0, 17);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Load file content to String
     * http://www.eoeandroid.com/thread-248775-1-1.html
     *
     * @param filePath
     * @return
     * @throws java.io.IOException
     * @author 史伟成 E-mail:495095492@qq.com 电话：15216801944
     * @version 创建时间：2015-7-9 上午10:45:32
     */
    public String loadFileAsString(String filePath) throws java.io.IOException {
        StringBuffer fileData = new StringBuffer(1000);
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead = 0;
        while ((numRead = reader.read(buf)) != -1) {
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
        }
        reader.close();
        return fileData.toString();
    }

    /**
     * 判断网络是否连接
     *
     * @param context
     * @return
     * @author 史伟成 E-mail:495095492@qq.com 电话：15216801944
     * @version 创建时间：2015-1-9 下午1:03:11
     */
    public boolean isHaveInternet(final Context context) {
        try {
            ConnectivityManager manger = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manger.getActiveNetworkInfo();
            return (info != null && info.isConnected());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * make true current connect service is wifi
     *
     * @param mContext
     * @return
     * @author 史伟成 E-mail:495095492@qq.com 电话：15216801944
     * @version 创建时间：2015-8-18 下午5:14:01
     */
    public boolean isWifi(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    /**
     * 获取网络状态
     *
     * @param context
     * @return
     * @author 史伟成 E-mail:495095492@qq.com 电话：15216801944
     * @version 创建时间：2016-6-24 上午11:48:21
     */
    public String getNetworkType(final Context context) {
        String strNetworkType = "";
        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                strNetworkType = "WIFI";
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                String _strSubTypeName = networkInfo.getSubtypeName();
                // TD-SCDMA networkType is 17
                int networkType = networkInfo.getSubtype();
                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN: // api<8 : replace by11
                        strNetworkType = "2G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B: // api<9 : replace by
                        // 14
                    case TelephonyManager.NETWORK_TYPE_EHRPD: // api<11 : replace by
                        // 12
                    case TelephonyManager.NETWORK_TYPE_HSPAP: // api<13 : replace by
                        // 15
                        strNetworkType = "3G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE: // api<11 : replace by
                        // 13
                        strNetworkType = "4G";
                        break;
                    default:
                        // http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
                        if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA") || _strSubTypeName.equalsIgnoreCase("WCDMA")
                                || _strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                            strNetworkType = "3G";
                        } else {
                            strNetworkType = _strSubTypeName;
                        }

                        break;
                }
            }
        }
        return strNetworkType;
    }
}
