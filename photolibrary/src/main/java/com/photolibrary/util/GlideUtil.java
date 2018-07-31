package com.photolibrary.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

/**
 * @author Ruking.Cheng
 * @descrilbe Glide工具
 * @email 495095492@qq.com
 * @tel 18075121944
 * @date on 2018/6/20 下午4:25
 */
public class GlideUtil {

    public static void load(@Nullable Context context, String url, @Nullable ImageView photoView) {
        load(context, null, url, photoView);
    }

    public static void load(@Nullable Context context, String url, @Nullable ImageView photoView, int mipmap) {
        load(context, null, url, photoView, mipmap);
    }

    public static void load(@Nullable Context context, View progressView, String url, @Nullable ImageView photoView) {
        load(context, progressView, url, photoView, 0);
    }

    @SuppressLint("CheckResult")
    public static void load(@Nullable Context context, View progressView, String url, @Nullable ImageView photoView,
                            int mipmap) {
//        RequestOptions options = new RequestOptions()
//                .placeholder(mipmap)	//加载成功之前占位图
//                .error(mipmap)	//加载错误之后的错误图
//                .override(400,400)	//指定图片的尺寸
//                //指定图片的缩放类型为fitCenter （等比例缩放图片，宽或者是高等于ImageView的宽或者是高。）
//                .fitCenter()
//                //指定图片的缩放类型为centerCrop （等比例缩放图片，直到图片的狂高都大于等于ImageView的宽度，然后截取中间的显示。）
//                .centerCrop()
//                .circleCrop()//指定图片的缩放类型为centerCrop （圆形）
//                .skipMemoryCache(true)	//跳过内存缓存
//                .diskCacheStrategy(DiskCacheStrategy.ALL)	//缓存所有版本的图像
//                .diskCacheStrategy(DiskCacheStrategy.NONE)	//跳过磁盘缓存
//                .diskCacheStrategy(DiskCacheStrategy.DATA)	//只缓存原来分辨率的图片
//                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)	//只缓存最终的图片
//                ;
        if (url == null) url = "";
        if (context == null) return;
        RequestManager requestManager = Glide.with(context);
        RequestBuilder<?> requestBuilder;
        if (url.endsWith(".gif")) {
            requestBuilder = requestManager.asGif().load(url)
                    .listener(new RequestListener<GifDrawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                            //ToastUtil.show(getContext(), "图片加载失败");
                            if (progressView != null)
                                progressView.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                            //ToastUtil.show(getContext(), "图片加载成功");
                            if (progressView != null)
                                progressView.setVisibility(View.GONE);
                            return false;
                        }
                    });
        } else {
            requestBuilder = requestManager.load(url).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    //ToastUtil.show(getContext(), "图片加载失败");
                    if (progressView != null)
                        progressView.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    //ToastUtil.show(getContext(), "图片加载成功");
                    if (progressView != null)
                        progressView.setVisibility(View.GONE);
                    return false;
                }

            });
        }
        if (mipmap != 0) {
            RequestOptions options = new RequestOptions()
                    .placeholder(mipmap)    //加载成功之前占位图
                    .error(mipmap)    //加载错误之后的错误图
                    ;
            requestBuilder.apply(options);
        }
        if (photoView != null) {
            requestBuilder.into(photoView);
        }
    }
}
