package com.photolibrary.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoView;
import com.photolibrary.R;
import com.photolibrary.bean.ImageAttr;
import com.photolibrary.util.GlideUtil;

public class ImagesFragment extends Fragment {
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_photoview, container, false);
        View progressView = view.findViewById(R.id.progressView);
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
        if (url != null) {
            GlideUtil.load(getContext(), progressView, url, photoView, R.mipmap.wuxianshitupian);
        }
        return view;
    }
}
