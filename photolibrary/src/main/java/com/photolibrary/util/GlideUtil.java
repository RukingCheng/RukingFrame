package com.photolibrary.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

/**
 * @author Ruking.Cheng
 * @descrilbe Glide工具
 * @email 495095492@qq.com
 * @tel 18075121944
 * @date on 2018/6/20 下午4:25
 */
public class GlideUtil {

    public static void load(Context context, String url, ImageView photoView) {
        load(context, null, url, photoView);
    }

    public static void load(Context context, String url, ImageView photoView, int mipmap) {
        load(context, null, url, photoView, mipmap);
    }

    public static void load(Context context, View progressView, String url, ImageView photoView) {
        load(context, progressView, url, photoView, 0);
    }

    public static void load(Context context, View progressView, String url, ImageView photoView,
                            int mipmap) {
        RequestManager requestManager = Glide.with(context);
        if (mipmap != 0)
            photoView.setImageResource(mipmap);
        if (url.endsWith(".gif")) {
            requestManager.asGif().load(url).listener(new RequestListener<GifDrawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                    //ToastUtil.show(getContext(), "图片加载失败");
                    if (progressView != null)
                        progressView.setVisibility(View.GONE);
                    if (mipmap != 0)
                        photoView.setImageResource(mipmap);
                    return false;
                }

                @Override
                public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                    //ToastUtil.show(getContext(), "图片加载成功");
                    if (progressView != null)
                        progressView.setVisibility(View.GONE);
                    return false;
                }
            }).into(photoView);
        } else {
            requestManager.load(url).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    //ToastUtil.show(getContext(), "图片加载失败");
                    if (progressView != null)
                        progressView.setVisibility(View.GONE);
                    if (mipmap != 0)
                        photoView.setImageResource(mipmap);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    //ToastUtil.show(getContext(), "图片加载成功");
                    if (progressView != null)
                        progressView.setVisibility(View.GONE);
                    return false;
                }

            }).into(photoView);
        }
    }
}
