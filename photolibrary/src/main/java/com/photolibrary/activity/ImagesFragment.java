package com.photolibrary.activity;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.github.chrisbanes.photoview.PhotoView;
import com.photolibrary.R;
import com.photolibrary.bean.ImageAttr;
import com.photolibrary.progress.CircleProgressView;
import com.photolibrary.util.GlideImageLoader;

public class ImagesFragment extends Fragment {
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_photoview, container, false);
        CircleProgressView progressView = view.findViewById(R.id.progressView);
        PhotoView photoView = view.findViewById(R.id.photoView);
        photoView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        photoView.setOnPhotoTapListener((view1, x, y) -> {
            if (getActivity() != null) {
                ((ImagesActivity) getActivity()).finishWithAnim();
            }
        });
        photoView.setOnOutsidePhotoTapListener(imageView -> {
            if (getActivity() != null) {
                ((ImagesActivity) getActivity()).finishWithAnim();
            }
        });
        ImageAttr attr = (ImageAttr) getArguments().getSerializable("data");
        String url = null;
        if (attr != null) {
            url = TextUtils.isEmpty(attr.thumbnailUrl) ? attr.url : attr.thumbnailUrl;
        }
        GlideImageLoader imageLoader = GlideImageLoader.create(photoView);
        imageLoader.setOnGlideImageViewListener(url, (percent, isDone, exception) -> {
            progressView.setProgress(percent);
            progressView.setVisibility(isDone ? View.GONE : View.VISIBLE);
        });
        RequestOptions requestOptions = imageLoader.requestOptions(R.color.placeholder_color)
                .centerCrop()
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
        final RequestBuilder<Drawable> requestBuilder = imageLoader.requestBuilder(url, requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade());
        requestBuilder.into(new SimpleTarget<Drawable>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
            @Override
            public void onResourceReady(@NonNull Drawable resource, Transition<? super Drawable> transition) {
                //if (resource.getIntrinsicHeight() > DisplayUtil.getScreenHeight(mContext)) {
                // photoView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                // }
                requestBuilder.into(photoView);
            }

        });
        return view;
    }
}
