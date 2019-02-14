package com.photolibrary.bean;

import java.io.Serializable;

public class ImageAttr implements Serializable {

    public String url;
    public String thumbnailUrl;
    // 显示的宽高
    public int width;
    public int height;
    // 左上角坐标
    public int left;
    public int top;
    public int type;//0:图片，1：视频

    public String imageId;
    public boolean isSelected = false;

}
