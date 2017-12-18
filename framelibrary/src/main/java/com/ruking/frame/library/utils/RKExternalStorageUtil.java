package com.ruking.frame.library.utils;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * 获取SD卡路径
 *
 * @author 史伟成 E-mail:495095492@qq.com 电话：15216801944
 * @version 创建时间：2015-8-4 下午5:30:44
 */
public class RKExternalStorageUtil {

    public static final String SD_CARD = "sdCard";
    public static final String EXTERNAL_SD_CARD = "externalSdCard";
    private static RKExternalStorageUtil externalStorage;

    private RKExternalStorageUtil() {
    }

    /**
     * 获取SDCard的目录路径功能
     *
     * @return
     * @author 史伟成 E-mail:495095492@qq.com 电话：15216801944
     * @version 创建时间：2015-1-9 下午1:02:58
     */
    public static String getSDCardPath() {
        if (externalStorage == null) {
            externalStorage = new RKExternalStorageUtil();
        }
        Map<String, File> map = externalStorage.getAllStorageLocations();
        String s = map.get(RKExternalStorageUtil.SD_CARD).toString();
        if (!s.equals("") && !s.equals("null")
                && !(s.equals("/mnt/sdcard") && !new RKProjectUtil().isWritable())) {
            return s;
        }
        return "/mnt";
    }

    private Map<String, File> map;

    /**
     * 获取可用内存路径
     *
     * @return A map of all storage locations available
     * @author 史伟成 E-mail:495095492@qq.com 电话：15216801944
     * @version 创建时间：2015-8-4 下午5:32:13
     */
    private Map<String, File> getAllStorageLocations() {
        if (map != null)
            return map;
        map = new HashMap<>(10);
        List<String> mMounts = new ArrayList<>(10);
        List<String> mVold = new ArrayList<>(10);
        mMounts.add("/mnt/sdcard");
        mVold.add("/mnt/sdcard");
        try {
            File mountFile = new File("/proc/mounts");
            if (mountFile.exists()) {
                Scanner scanner = new Scanner(mountFile);
                while (scanner.hasNext()) {
                    String line = scanner.nextLine();
                    if (line.startsWith("/dev/block/vold/")) {
                        String[] lineElements = line.split(" ");
                        String element = lineElements[1];
                        if (!element.equals("/mnt/sdcard"))
                            mMounts.add(element);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            File voldFile = new File("/system/etc/vold.fstab");
            if (voldFile.exists()) {
                Scanner scanner = new Scanner(voldFile);
                while (scanner.hasNext()) {
                    String line = scanner.nextLine();
                    if (line.startsWith("dev_mount")) {
                        String[] lineElements = line.split(" ");
                        String element = lineElements[2];
                        if (element.contains(":"))
                            element = element
                                    .substring(0, element.indexOf(":"));
                        if (!element.equals("/mnt/sdcard"))
                            mVold.add(element);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < mMounts.size(); i++) {
            String mount = mMounts.get(i);
            if (!mVold.contains(mount))
                mMounts.remove(i--);
        }
        mVold.clear();
        List<String> mountHash = new ArrayList<>(10);
        for (String mount : mMounts) {
            File root = new File(mount);
            if (root.exists() && root.isDirectory() && root.canWrite()) {
                File[] list = root.listFiles();
                String hash = "[";
                if (list != null) {
                    for (File f : list) {
                        hash += f.getName().hashCode() + ":" + f.length()
                                + ", ";
                    }
                }
                hash += "]";
                if (!mountHash.contains(hash)) {
                    String key = SD_CARD + "_" + map.size();
                    if (map.size() == 0) {
                        key = SD_CARD;
                    } else if (map.size() == 1) {
                        key = EXTERNAL_SD_CARD;
                    }
                    mountHash.add(hash);
                    map.put(key, root);
                }
            }
        }
        mMounts.clear();
        if (map.isEmpty()) {
            map.put(SD_CARD, Environment.getExternalStorageDirectory());
        }
        return map;
    }
}