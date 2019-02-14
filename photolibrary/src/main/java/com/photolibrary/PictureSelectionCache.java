package com.photolibrary;

import android.app.Activity;

import com.photolibrary.bean.ImageAttr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ruking.Cheng
 * @descrilbe TODO
 * @email 495095492@qq.com
 * @tel 18075121944
 * @date on 2018/5/14 下午8:52
 */
public class PictureSelectionCache {
    public static final int PICTURE_SELECTION_CACHE = 0xab123;
    public static Map<String, ImageAttr> tempSelectBitmap = new HashMap<>();
    public static int num = 9;
    public static boolean isVideo = false;
    public static Activity activity;

    public static List<ImageAttr> getImagetAttr() {
        List<ImageAttr> imageAttrs = new ArrayList<>();
        Collection<ImageAttr> collection = tempSelectBitmap.values();
        imageAttrs.addAll(collection);
        return imageAttrs;
    }

    public static void clear() {
        tempSelectBitmap.clear();
        if (activity != null) {
            activity.finish();
            activity = null;
        }
    }
}
