package com.photolibrary.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.photolibrary.PictureSelectionCache;
import com.photolibrary.R;
import com.photolibrary.activity.ImagesActivity;
import com.photolibrary.bean.ImageAttr;
import com.photolibrary.util.GlideUtil;
import com.ruking.frame.library.utils.RKWindowUtil;
import com.ruking.frame.library.view.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ruking.Cheng
 * @descrilbe TODO
 * @email 495095492@qq.com
 * @tel 18075121944
 * @date on 2018/5/14 上午11:05
 */
public abstract class PictureSelectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<ImageAttr> imageAttrs;
    private int what;

    public PictureSelectionAdapter(@NonNull Context mContext, int what) {
        this.what = what;
        this.mContext = mContext;
    }

    public void setImageAttrs(@NonNull List<ImageAttr> imageAttrs) {
        this.imageAttrs = imageAttrs;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.itme_picture,
                parent, false);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                (int) (RKWindowUtil.getScreenWidth(mContext) / 4.0)));
        return new DescViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final DescViewHolder descHolder = (DescViewHolder) holder;
        ImageAttr attr = imageAttrs.get(position);
        String url = TextUtils.isEmpty(attr.thumbnailUrl) ? attr.url : attr.thumbnailUrl;
        GlideUtil.load(mContext, url, descHolder.imageView, R.mipmap.wuxianshitupian);
        if (PictureSelectionCache.tempSelectBitmap.containsKey(attr.url)) {
            descHolder.itemChoose.setImageResource(R.mipmap.rk_choose_normal);
            descHolder.itemChoose.setColorFilter(ContextCompat.getColor(mContext, R.color.colorAccent));
        } else {
            descHolder.itemChoose.setImageResource(R.mipmap.rk_choose_checked);
            descHolder.itemChoose.setColorFilter(null);
        }
        descHolder.but.setOnClickListener(view -> {
            List<ImageAttr> imageAttrs2;
            int position2;
            if (imageAttrs.size() > 1000) {
                imageAttrs2 = new ArrayList<>();
                int j = position / 1000;
                position2 = position % 1000;
                for (int i = 1000 * j; i < ((1000 * (j + 1)) > imageAttrs.size() ? imageAttrs.size() : (1000 * (j + 1))); i++) {
                    imageAttrs2.add(imageAttrs.get(i));
                }
            } else {
                position2 = position;
                imageAttrs2 = imageAttrs;
            }
            int width = descHolder.imageView.getWidth();
            int height = descHolder.imageView.getHeight();
            int[] points1 = new int[2];
            descHolder.imageView.getLocationInWindow(points1);
            for (ImageAttr attr1 : imageAttrs2) {
                attr1.width = width;
                attr1.height = height;
                attr1.left = points1[0];
                attr1.top = points1[1];
            }
            ImagesActivity.starImageAttrsActivity((Activity) mContext, imageAttrs2, position2, 1, what);
        });

        descHolder.itemChoose.setOnClickListener(view -> {
            if (PictureSelectionCache.tempSelectBitmap.containsKey(attr.url)) {
                attr.isSelected = false;
                PictureSelectionCache.tempSelectBitmap.remove(attr.url);
            } else {
                if (PictureSelectionCache.tempSelectBitmap.size() >= PictureSelectionCache.num) {
                    ToastUtil.show(mContext, R.string.only_choose_num);
                    return;
                }
                attr.isSelected = true;
                PictureSelectionCache.tempSelectBitmap.put(attr.url, attr);
            }
            notifyDataSetChanged();
            showTemp();
        });
    }

    public abstract void showTemp();

    @Override
    public int getItemCount() {
        if (imageAttrs != null) {
            return imageAttrs.size();
        } else {
            return 0;
        }
    }

    private static class DescViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private ImageView itemChoose;
        private View but;

        @SuppressLint("CutPasteId")
        DescViewHolder(View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.image_view);
            this.itemChoose = itemView.findViewById(R.id.item_choose);
            this.but = itemView.findViewById(R.id.but);
        }
    }

}