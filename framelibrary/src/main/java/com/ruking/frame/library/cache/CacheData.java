package com.ruking.frame.library.cache;

import org.json.JSONArray;

import java.io.File;

/**
 * @author Ruking.Cheng
 * @descrilbe 数据缓存
 * @email 495095492@qq.com
 * @tel 18075121944
 * @date on 2018/7/4 下午3:04
 */
public class CacheData {
    protected JsonDataPreferences preferences;

    public CacheData(File cacheFile, String fileName) {
        preferences = new JsonDataPreferences(cacheFile, fileName);
    }

    protected void setString(String s, String v, boolean isCommit) {
        preferences.setString(s, v);
        if (isCommit) {
            preferences.commit();
        }
    }

    protected String getString(String k) {
        return preferences.getString(k, "");
    }

    protected void setInt(String s, int v, boolean isCommit) {
        preferences.setInt(s, v);
        if (isCommit) {
            preferences.commit();
        }
    }

    protected int getInt(String k) {
        return preferences.getInt(k, 0);
    }

    protected void setBoolean(String s, boolean v, boolean isCommit) {
        preferences.setBoolean(s, v);
        if (isCommit) {
            preferences.commit();
        }
    }

    protected boolean getBoolean(String k) {
        return getBoolean(k, false);
    }

    protected boolean getBoolean(String k, boolean v) {
        return preferences.getBoolean(k, v);
    }

    protected void setDouble(String s, Double v, boolean isCommit) {
        preferences.setDouble(s, v);
        if (isCommit) {
            preferences.commit();
        }
    }

    protected double getDouble(String k) {
        return preferences.getDouble(k);
    }

    protected void setBooleanDefaultTrue(String s, boolean v) {
        preferences.setBoolean(s, v);
        preferences.commit();
    }

    protected boolean getBooleanDefaultTrue(String k) {
        return preferences.getBoolean(k, true);
    }

    protected void setJSONArray(String s, JSONArray v) {
        preferences.setJSONArray(s, v);
        preferences.commit();

    }

    protected JSONArray getJSONArray(String k) {
        return preferences.getJSONArray(k);
    }
}
