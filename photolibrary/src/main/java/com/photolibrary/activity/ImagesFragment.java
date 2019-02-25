package com.photolibrary.activity;

import android.annotation.SuppressLint;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.ImageViewState;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.github.chrisbanes.photoview.PhotoView;
import com.photolibrary.R;
import com.photolibrary.bean.ImageAttr;
import com.photolibrary.util.GlideUtil;
import com.photolibrary.widget.jcvideoplayer.JCVideoPlayerStandard;
import com.ruking.frame.library.view.ToastUtil;

import java.io.File;

public class ImagesFragment extends Fragment {
    @SuppressLint({"SetTextI18n", "CheckResult"})
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_photoview, container, false);
        View progressView = view.findViewById(R.id.progressView);
        View start = view.findViewById(R.id.start);
        PhotoView photoView = view.findViewById(R.id.photoView);
        SubsamplingScaleImageView subsamplingScaleImageView = view.findViewById(R.id.subsamplingScaleImageView);
        subsamplingScaleImageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CUSTOM);
        subsamplingScaleImageView.setMinScale(0.2F);
        subsamplingScaleImageView.setMaxScale(5F);
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
        subsamplingScaleImageView.setOnClickListener(v -> {
            if (getActivity() != null) {
                ((ImagesActivity) getActivity()).finishWithAnim();
            }
        });
        ImageAttr attr = (ImageAttr) getArguments().getSerializable("data");
        String url = null;
        if (attr != null) {
            url = TextUtils.isEmpty(attr.thumbnailUrl) ? attr.url : attr.thumbnailUrl;
            if (attr.type == 1) {
                photoView.setZoomable(false);
                start.setVisibility(View.VISIBLE);
                String finalUrl1 = url;
                start.setOnClickListener(v -> {
                    try {// 视频播放
                        JCVideoPlayerStandard.startFullscreen(getActivity(), JCVideoPlayerStandard.class, finalUrl1, attr.imageId);
                    } catch (Exception e) {
                        e.printStackTrace();
                        ToastUtil.show(getActivity(), "无法打开该类型的文件");
                    }
                });
            } else {
                photoView.setZoomable(true);
                start.setVisibility(View.GONE);
            }
        }
        if (url != null) {
            if ((url.startsWith("http://") || url.startsWith("https://")) && (url.endsWith(".jpg") || url.endsWith(".JPG"))) {
                photoView.setVisibility(View.GONE);
                subsamplingScaleImageView.setVisibility(View.VISIBLE);
                //下载图片保存到本地
                Glide.with(this).load(url).downloadOnly(new SimpleTarget<File>() {
                    @Override
                    public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
                        // 将保存的图片地址给SubsamplingScaleImageView,这里注意设置ImageViewState设置初始显示比例
                        if (progressView != null)
                            progressView.setVisibility(View.GONE);
                        subsamplingScaleImageView.setImage(ImageSource.uri(Uri.fromFile(resource)), new
                                ImageViewState(1F, new PointF(0, 0), 0));

                    }
                });
            } else {
                photoView.setVisibility(View.VISIBLE);
                subsamplingScaleImageView.setVisibility(View.GONE);
                GlideUtil.load(getContext(), progressView, url, photoView, R.mipmap.wuxianshitupian);
            }
            if (ImagesActivity.onLongClickImageListener != null) {
                String finalUrl = url;
                photoView.setOnLongClickListener(view12 -> {
                    if (ImagesActivity.onLongClickImageListener != null) ImagesActivity
                            .onLongClickImageListener.onLongClick(view12, finalUrl);
                    return false;
                });
                subsamplingScaleImageView.setOnLongClickListener(view12 -> {
                    if (ImagesActivity.onLongClickImageListener != null) ImagesActivity
                            .onLongClickImageListener.onLongClick(view12, finalUrl);
                    return false;
                });
            }
        }
        return view;
    }
}
