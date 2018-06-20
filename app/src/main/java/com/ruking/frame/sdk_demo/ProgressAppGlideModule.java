package com.ruking.frame.sdk_demo;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.AppGlideModule;
import com.ruking.frame.library.utils.RKExternalStorageUtil;

import java.io.File;

@GlideModule
public class ProgressAppGlideModule extends AppGlideModule {

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        // 设置磁盘缓存为100M，缓存在内部缓存目录
        int cacheSizeBytes = 1024 * 1024 * 100;
        File cacheFile = new File(RKExternalStorageUtil.getSDCardPath(), context.getPackageName());
        builder.setDiskCache(new DiskLruCacheFactory(cacheFile.getAbsolutePath(), cacheSizeBytes));

//        // 20%大的内存缓存作为 Glide 的默认值
//        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context).build();
//        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
//        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();
//
//        int customMemoryCacheSize = (int) (1.2 * defaultMemoryCacheSize);
//        int customBitmapPoolSize = (int) (1.2 * defaultBitmapPoolSize);
//
//        builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));
//        builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));

    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
    }
}