package com.photolibrary.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.photolibrary.PictureSelectionCache;
import com.photolibrary.R;
import com.photolibrary.adapter.GridItemDecoration;
import com.photolibrary.adapter.PictureSelectionAdapter;
import com.photolibrary.bean.ImageAttr;
import com.photolibrary.bean.ImageBucket;
import com.photolibrary.util.AlbumHelper;
import com.ruking.frame.library.bean.Choice;
import com.ruking.frame.library.rxbus.RxBus;
import com.ruking.frame.library.view.ToastUtil;
import com.ruking.frame.library.view.animation.RKAnimationButton;
import com.ruking.frame.library.widget.RKDialog;
import com.ruking.frame.library.widget.RKDialogChoiceList;
import com.ruking.frame.library.widget.RKDialogProfile;
import com.zhy.autolayout.AutoRecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ruking.Cheng
 * @descrilbe TODO
 * @email 495095492@qq.com
 * @tel 18075121944
 * @date on 2018/5/14 上午9:16
 */
public class PictureSelectionModular {
    private Activity activity;
    private View rootView;
    private TextView albumTv;
    private RKAnimationButton albumBut;
    private PictureSelectionAdapter adapter;
    private List<ImageBucket> contentList;
    private int type = -1;
    private int what = PictureSelectionCache.PICTURE_SELECTION_CACHE;

    public static Intent getPictureSelectionModularIntent(@NonNull Activity activity, @NonNull
            Class<?> cls, @NonNull List<ImageAttr> images) {
        return getPictureSelectionModularIntent(activity, cls, images, PictureSelectionCache
                .PICTURE_SELECTION_CACHE);
    }

    public static Intent getPictureSelectionModularIntent(@NonNull Activity activity, @NonNull Class<?>
            cls, @NonNull List<ImageAttr> images, int what) {
        Intent intent = new Intent(activity, cls);
        Bundle bundle = new Bundle();
        bundle.putSerializable(ImagesActivity.IMAGE_ATTR, (Serializable) images);
        bundle.putInt("what", what);
        intent.putExtras(bundle);
        return intent;
    }

    @SuppressLint("InflateParams")
    public PictureSelectionModular(Activity activity, FrameLayout layout, Intent intent) {
        if (intent != null) {
            List<ImageAttr> imageAttrs = (List<ImageAttr>) intent.getSerializableExtra(ImagesActivity
                    .IMAGE_ATTR);
            what = intent.getIntExtra("what", PictureSelectionCache.PICTURE_SELECTION_CACHE);
            if (imageAttrs != null)
                for (ImageAttr attr : imageAttrs)
                    PictureSelectionCache.tempSelectBitmap.put(attr.url, attr);
        }
        this.activity = activity;
        PictureSelectionCache.activity = activity;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            rootView = inflater.inflate(R.layout.picture_selection_modular, null);
            layout.removeAllViews();
            layout.addView(rootView);
            init();
        }
    }

    public void onResume() {
        adapter.notifyDataSetChanged();
    }

    private void init() {
        AutoRecyclerView imgList = rootView.findViewById(R.id.img_list);
        albumTv = rootView.findViewById(R.id.album_tv);
        albumBut = rootView.findViewById(R.id.album_but);
        adapter = new PictureSelectionAdapter(activity, what) {
            @SuppressLint("SetTextI18n")
            @Override
            public void showTemp() {
                if (PictureSelectionCache.tempSelectBitmap.size() <= 0) {
                    albumBut.setText(R.string.album_complete);
                } else
                    albumBut.setText(activity.getString(R.string.album_complete) + "(" +
                            PictureSelectionCache
                                    .tempSelectBitmap.size() + "/" + PictureSelectionCache.num + ")");
            }
        };
        adapter.showTemp();
        GridLayoutManager mgr = new GridLayoutManager(activity, 4);
        imgList.setLayoutManager(mgr);
        imgList.addItemDecoration(new GridItemDecoration());
        imgList.getItemAnimator().setChangeDuration(0);
        imgList.setAdapter(adapter);
        AlbumHelper helper = AlbumHelper.getHelper(activity);
        contentList = helper.getImagesBucketList(false);
        showAlbum(type);
        albumTv.setOnClickListener(view -> {
            List<Choice> choices = new ArrayList<>();
            choices.add(new Choice(activity.getString(R.string.album_all), type == -1).setTag(-1));
            for (int i = 0; i < contentList.size(); i++) {
                choices.add(new Choice(contentList.get(i).bucketName + "\t(" + contentList.get(i)
                        .count + ")",
                        type == i).setTag(i));
            }
            new RKDialog.Builder(activity)
                    .setBottomDisplay(true)
                    .addProfile(new RKDialogProfile(RKDialogProfile.DIALOG_BACKGROUND)
                            .setStrokeWidth(0)
                            .setBackgroundColorRes(R.color.album_bg)
                            .setRroundCorner(0)
                    )
                    .setDialogChoiceList(
                            new RKDialogChoiceList(activity)
                                    .setChoices(choices)
                                    .setProfile(new RKDialogProfile()
                                            .setTextColorRes(R.color.white)
                                            .setItmeColorRes(R.color.colorAccent))
                                    .setOnSingleChoiceSelectListener((dialog, choice, position) -> {
                                        showAlbum((Integer) choice.getTag());
                                        dialog.dismiss();
                                    }))
                    .show();
        });
        albumBut.setOnClickListener(view -> {
            if (PictureSelectionCache.tempSelectBitmap.size() <= 0) {
                ToastUtil.show(activity, R.string.album_err);
                return;
            }
            Message message = new Message();
            message.what = what;
            message.obj = PictureSelectionCache.getImagetAttr();
            RxBus.getDefault().post(message);
            PictureSelectionCache.clear();
        });
    }

    @SuppressLint("SetTextI18n")
    private void showAlbum(int type) {
        List<ImageAttr> dataList = new ArrayList<>();
        if (type >= contentList.size()) type = -1;
        if (type == -1) {
            for (int i = 0; i < contentList.size(); i++) {
                dataList.addAll(contentList.get(i).imageList);
            }
            albumTv.setText(activity.getString(R.string.album_all) + "\t\t▼");
        } else {
            dataList = contentList.get(type).imageList;
            albumTv.setText(contentList.get(type).bucketName + "\t\t▼");
        }
        this.type = type;
        adapter.setImageAttrs(dataList);
    }

    public void onDestroy() {
        PictureSelectionCache.clear();
    }
}
