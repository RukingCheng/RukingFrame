package com.ruking.frame.library.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.TypedValue;

import java.text.DecimalFormat;

/**
 * 单位转换
 *
 * @author 史伟成 E-mail:495095492@qq.com 电话：18075121944
 * @time 创建时间：2017/5/6 15:20
 */

public class RKConversionUtil {
    /**
     * 得到double后两位
     *
     * @param d
     * @return
     * @author 史伟成 E-mail:495095492@qq.com 电话：15216801944
     * @version 创建时间：2015-3-12 上午11:46:40
     */
    public double getDouble(double d) {
        return Double.valueOf(new DecimalFormat("##########.##").format(d));
    }

    /**
     * 转dip
     *
     * @param i
     * @return
     * @author 史伟成 E-mail:495095492@qq.com 电话：15216801944
     * @version 创建时间：2015-4-17 下午2:06:17
     */
    public int roundDIP(final Context context, int i) {
        return Math.round(
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, i, context.getResources().getDisplayMetrics()));
    }

    /**
     * 将16进制byte[]转换成String 例如:{0x01,0x23,0x45,0x67,0x89,0xAB,0xCD,0xEF} ->
     * 0123456789ABCDEF
     *
     * @param b
     * @return
     * @author 史伟成 E-mail:495095492@qq.com 电话：15216801944
     * @version 创建时间：2015-7-9 上午10:45:20
     */
    public String getHexString(@NonNull byte[] b) {
        StringBuilder result = new StringBuilder();
        for (byte aB : b) {
            result.append(Integer.toString((aB & 0xff) + 0x100, 16).substring(1));
        }
        return result.toString();
    }

    /**
     * 将16进制String转换成byte[] 例如：0123456789ABCDEF
     * ->{0x01,0x23,0x45,0x67,0x89,0xAB,0xCD,0xEF}
     *
     * @param hex
     * @return
     * @throws IllegalArgumentException
     * @author 史伟成 E-mail:495095492@qq.com 电话：15216801944
     * @version 创建时间：2015-7-9 上午10:46:17
     */
    public byte[] hex2byte(@NonNull String hex) throws IllegalArgumentException {
        if (hex.length() % 2 != 0)
            hex += "0";
        char[] arr = hex.toCharArray();
        byte[] b = new byte[hex.length() / 2];
        for (int i = 0, j = 0, l = hex.length(); i < l; i++, j++) {
            String swap = "" + arr[i++] + arr[i];
            int byteint = Integer.parseInt(swap, 16) & 0xFF;
            b[j] = new Integer(byteint).byteValue();
        }
        return b;
    }

    /**
     * 得到1234 123
     *
     * @param sformat 12345678
     * @param d       多少位分割
     * @return
     * @author 史伟成 E-mail:495095492@qq.com 电话：15216801944
     * @version 创建时间：2015-7-9 上午10:48:14
     */
    public String getDivisionString(String sformat, int d) {
        int sl = sformat.length();
        StringBuffer sb = new StringBuffer();
        if (d != 0 && sl != 0) {
            for (int i = 1; i <= (sl % d == 0 ? sl / d : (sl / d + 1)); i++) {
                if (i != (sl % d == 0 ? sl / d : (sl / d + 1))) {
                    sb.append(sformat.substring(i * d - d, d * i));
                    sb.append(" ");
                } else {
                    sb.append(sformat.substring(i * d - d, sl));
                }
            }
            return sb + "";
        } else {
            return sformat;
        }
    }
}
