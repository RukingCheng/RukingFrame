package com.ruking.frame.library.cache;

import android.content.Context;

import com.ruking.frame.library.utils.RKExternalStorageUtil;
import com.ruking.frame.library.utils.encrypt.Des;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 自定义缓存机制
 *
 * @author 史伟成 E-mail:495095492@qq.com 电话：15216801944
 * @version 创建时间：2015-7-21 上午9:34:40
 */
public class JsonDataPreferences {
    private byte[] desKey = {0x12, 0x13, 0x14, 0x15, 0x16, 0x1A, 0x5B, 0x7C};// 加密KEY
    private JSONObject jsonObject;
    private String fileName;// 文件名
    // private Context context;
    private String savePath;// 文件保存路径

    /**
     * 自定义缓存机制
     *
     * @param context
     * @param fileName 文件名称
     * @param savePath 文件路径
     * @author 史伟成 E-mail:495095492@qq.com 电话：15216801944
     * @version 创建时间：2015-10-9 下午1:14:08
     */
    public JsonDataPreferences(Context context, String fileName, String savePath) {
        super();
        this.fileName = fileName;
        // this.context = context;
        this.savePath = savePath;
        getJsonObject();
    }

    /**
     * 自定义缓存机制
     *
     * @param context
     * @param fileName 文件名称
     * @param savePath 文件路径
     * @param desKey   自定义加密KEY
     * @author 史伟成 E-mail:495095492@qq.com 电话：15216801944
     * @version 创建时间：2015-10-9 下午1:15:36
     */
    public JsonDataPreferences(Context context, String fileName, String savePath, byte[] desKey) {
        super();
        this.desKey = desKey;
        this.fileName = fileName;
        // this.context = context;
        this.savePath = savePath;
        getJsonObject();
    }

    public void setLong(String s, long v) {
        try {
            jsonObject.put(s, v);
        } catch (Exception e) {
        }
    }

    public long getLong(String k) {
        return jsonObject.optLong(k);
    }

    public long getLong(String k, long v) {
        return jsonObject.optLong(k, v);
    }

    public void setDouble(String s, double v) {
        try {
            jsonObject.put(s, v);
        } catch (Exception e) {
        }
    }

    public double getDouble(String k) {
        return jsonObject.optDouble(k);
    }

    public double getDouble(String k, double v) {
        return jsonObject.optDouble(k, v);
    }

    public void setString(String s, String v) {
        try {
            jsonObject.put(s, v);
        } catch (Exception e) {
        }
    }

    public String getString(String k) {
        return jsonObject.optString(k);
    }

    public String getString(String k, String v) {
        return jsonObject.optString(k, v);
    }

    public void setInt(String s, int v) {
        try {
            jsonObject.put(s, v);
        } catch (Exception e) {
        }
    }

    public int getInt(String k) {
        return jsonObject.optInt(k);
    }

    public int getInt(String k, int v) {
        return jsonObject.optInt(k, v);
    }

    public void setBoolean(String s, boolean v) {
        try {
            jsonObject.put(s, v);
        } catch (Exception e) {
        }
    }

    public boolean getBoolean(String k) {
        return jsonObject.optBoolean(k, false);
    }

    public boolean getBoolean(String k, boolean v) {
        return jsonObject.optBoolean(k, v);
    }

    public void setJSONArray(String s, JSONArray v) {
        try {
            jsonObject.put(s, v);
        } catch (Exception e) {
        }
    }

    public JSONArray getJSONArray(String k) {
        JSONArray array = jsonObject.optJSONArray(k);
        return array == null ? new JSONArray() : array;
    }

    public void setJSONObject(String s, JSONObject v) {
        try {
            jsonObject.put(s, v);
        } catch (Exception e) {
        }
    }

    public JSONObject getJSONObject(String k) {
        JSONObject object = jsonObject.optJSONObject(k);
        return object == null ? new JSONObject() : object;
    }

    public void commit() {
        writerPhoneFile(jsonObject.toString());
    }

    public JSONObject getJsonObject() {
        try {
            jsonObject = new JSONObject(readPhoneFile());
        } catch (Exception e) {
            jsonObject = new JSONObject();
        }
        return jsonObject;
    }

    public boolean writerPhoneFile(String data) {
        // sd卡检测
        // String sdStatus = Environment.getExternalStorageState();
        // if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
        // return false;
        // }
        try {
            // 得到文件输入流
            File path = new File(RKExternalStorageUtil.getSDCardPath() + savePath);
            String filepath = RKExternalStorageUtil.getSDCardPath() + savePath + File.separator + fileName;
            if (data != null && !data.equals("")) {
                File file = new File(filepath);
                if (!path.exists()) {
                    path.mkdirs();
                }
                if (file.exists()) {
                    file.delete();
                }
                file.createNewFile();
                FileWriter fw = new FileWriter(file);
                BufferedWriter buffw = new BufferedWriter(fw);
                PrintWriter pw = new PrintWriter(buffw);
                pw.println(encryption(data));
                pw.close();
                buffw.close();
                fw.close();
            } else {
                return false;
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * 加密
     *
     * @param data
     * @return
     * @author 史伟成 E-mail:495095492@qq.com 电话：15216801944
     * @version 创建时间：2015-7-21 上午10:35:46
     */
    private String encryption(String data) {
        data = new Des().encryptToString(data, desKey);
        if (data == null) {
            return "";
        }
        return data;
    }

    /**
     * 解密
     *
     * @param data
     * @return
     * @author 史伟成 E-mail:495095492@qq.com 电话：15216801944
     * @version 创建时间：2015-7-21 上午10:35:52
     */
    private String decryption(String data) {
        data = new Des().decrypt(data, desKey);
        if (data == null) {
            return "";
        }
        // Log.i("demo", data);
        return data;
    }

    public String readPhoneFile() {
        FileInputStream in = null;
        ByteArrayOutputStream outputStream = null;
        try {
            // 得到文件输入流
            File path2 = new File(RKExternalStorageUtil.getSDCardPath() + savePath);
            String filepath = RKExternalStorageUtil.getSDCardPath() + savePath + File.separator + fileName;
            File file = new File(filepath);
            if (!path2.exists()) {
                path2.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            in = new FileInputStream(file);
            byte Buffer[] = new byte[(int) file.length()];
            // 读出来的数据首先放入缓冲区，满了之后再写到字符输出流中
            int len = in.read(Buffer);
            // 创建一个字节数组输出流
            outputStream = new ByteArrayOutputStream();
            outputStream.write(Buffer, 0, len);
            // 把字节输出流转String
            return decryption(new String(outputStream.toByteArray()));
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }
}
