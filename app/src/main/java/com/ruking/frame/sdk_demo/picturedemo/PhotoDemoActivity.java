package com.ruking.frame.sdk_demo.picturedemo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.photolibrary.PictureSelectionCache;
import com.photolibrary.activity.ImagesActivity;
import com.photolibrary.activity.PictureSelectionModular;
import com.photolibrary.bean.ImageAttr;
import com.ruking.frame.library.base.RKBaseActivity;
import com.ruking.frame.library.base.RKTransitionMode;
import com.ruking.frame.library.rxbus.Subscribe;
import com.ruking.frame.library.rxbus.ThreadMode;
import com.ruking.frame.library.utils.RKExternalStorageUtil;
import com.ruking.frame.library.view.custom.RKFlowLayout;
import com.ruking.frame.library.widget.RKDialog;
import com.ruking.frame.library.widget.RKDialogButton;
import com.ruking.frame.library.widget.RKDialogProfile;
import com.ruking.frame.sdk_demo.R;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhy.autolayout.utils.AutoUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Ruking.Cheng
 * @descrilbe TODO
 * @email 495095492@qq.com
 * @tel 18075121944
 * @date on 2018/5/15 上午9:12
 */
public class PhotoDemoActivity extends RKBaseActivity {


    @BindView(R.id.bga_photos)
    RKFlowLayout bgaPhotos;
    private List<ImageAttr> imageAttrs = new ArrayList<>();
    private static final int TAKE_PICTURE = 0x000001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_demo);
        ButterKnife.bind(this);
        setSlidr();
        showImages();
    }

    @SuppressLint("InflateParams")
    private void showImages() {
        bgaPhotos.removeAllViews();
        ViewGroup.MarginLayoutParams mParams = new ViewGroup.MarginLayoutParams(
                AutoUtils.getPercentWidthSizeBigger(160),
                AutoUtils.getPercentWidthSizeBigger(160));
        int m = AutoUtils.getPercentWidthSizeBigger(10);
        mParams.setMargins(m, m, m, m);
        for (int i = 0; i < imageAttrs.size(); i++) {
            int arg2 = i;
            View itemView = getLayoutInflater().inflate(R.layout.itme_picture, null);
            itemView.setLayoutParams(mParams);
            ImageView imageView = itemView.findViewById(com.photolibrary.R.id.image_view);
            ImageView itemChoose = itemView.findViewById(com.photolibrary.R.id.item_choose);
            View but = itemView.findViewById(com.photolibrary.R.id.but);
            ImageAttr attr = imageAttrs.get(i);
            String url = TextUtils.isEmpty(attr.thumbnailUrl) ? attr.url : attr.thumbnailUrl;
            File file = new File(url);
            Glide.with(activity).load(file).into(imageView);
            but.setOnClickListener(view -> {
                for (ImageAttr attr1 : imageAttrs) {
                    attr1.width = imageView.getWidth();
                    attr1.height = imageView.getHeight();
                    int[] points1 = new int[2];
                    imageView.getLocationInWindow(points1);
                    attr1.left = points1[0];
                    attr1.top = points1[1];
                }
                ImagesActivity.starImageAttrsActivity(activity, imageAttrs, arg2, 2);
            });
            itemChoose.setImageResource(R.mipmap.cancel);
            itemChoose.setOnClickListener(view -> {
                imageAttrs.remove(attr);
                showImages();
            });
            bgaPhotos.addView(itemView);
        }
        if (imageAttrs.size() < PictureSelectionCache.num) {
            View itemView = getLayoutInflater().inflate(R.layout.itme_picture, null);
            itemView.setLayoutParams(mParams);
            ImageView imageView = itemView.findViewById(com.photolibrary.R.id.image_view);
            ImageView itemChoose = itemView.findViewById(com.photolibrary.R.id.item_choose);
            View but = itemView.findViewById(com.photolibrary.R.id.but);
            imageView.setImageResource(R.drawable.icon_addpic_unfocused);
            itemChoose.setVisibility(View.GONE);
            but.setOnClickListener(view -> new RKDialog.Builder(activity)
                    .setTitle("照片选择")
                    .setButtonLayoutOrientation(LinearLayout.VERTICAL)
                    .setBottomDisplay(true)
                    .addButton(new RKDialogButton(activity)
                                    .setText("拍照")
                                    .setProfile(new RKDialogProfile()
                                            .setBackgroundColorRes(R.color.colorPrimary)
                                            .setTextColorRes(android.R.color.white))
                                    .setOnClickListener((dialog, button) -> {
                                        RxPermissions rxPermissions = new RxPermissions(this);
                                        rxPermissions.request(Manifest.permission.CAMERA)
                                                .subscribe(granted -> {
                                                    if (granted) { // Always true pre-M
//                                                Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                                                startActivityForResult(openCameraIntent, TAKE_PICTURE);
                                                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //调用系统相机
                                                        startActivityForResult(intent, TAKE_PICTURE);
                                                    }
                                                });
                                        dialog.dismiss();
                                    })
                    )
                    .addButton(new RKDialogButton(activity)
                            .setText("相册")
                            .setProfile(new RKDialogProfile()
                                    .setBackgroundColorRes(R.color.colorPrimary)
                                    .setTextColorRes(android.R.color.white))
                            .setOnClickListener((dialog, button) -> {
                                startActivity(PictureSelectionModular.getPictureSelectionModularIntent
                                        (activity, PictureActivity.class, imageAttrs));
                                dialog.dismiss();
                            })
                    )
                    .addButton(new RKDialogButton(activity)
                            .setText("取消")
                            .setProfile(new RKDialogProfile()
                                    .setStrokeColor(Color.parseColor("#aaaaaa"))
                                    .setTextColor(Color.parseColor("#aaaaaa")))
                            .setOnClickListener((dialog, button) -> dialog.dismiss())
                    ).show());
            bgaPhotos.addView(itemView);
        }
    }

    public File saveBitmap(Bitmap bm) {
        File f = null;
        try {
            String fileName = DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
            f = new File(RKExternalStorageUtil.getSDCardPath() + "/" + activity.getPackageName(),
                    fileName);
            if (f.exists()) {
                f.delete();
            }
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(Message message) {
        if (message.what == PictureSelectionCache.PICTURE_SELECTION_CACHE) {
            imageAttrs = (List<ImageAttr>) message.obj;
            showImages();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PICTURE:
                if (imageAttrs.size() < PictureSelectionCache.num && resultCode == RESULT_OK) {
                    Bitmap bm = (Bitmap) data.getExtras().get("data");
                    File picture = saveBitmap(bm);
                    if (picture != null) {
                        Uri uri = Uri.fromFile(picture);
                        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                        intent.setData(uri);
                        sendBroadcast(intent);
                        ImageAttr attr = new ImageAttr();
                        attr.url = picture.getAbsolutePath();
                        imageAttrs.add(attr);
                        showImages();
                    }
                }
                break;
        }
    }

    @Override
    public boolean isRxBusHere() {
        return true;
    }

    @Override
    public int getStatusBarColor() {
//        return Color.parseColor("#000");
        return ContextCompat.getColor(activity, R.color.color_9000);
    }

    @Override
    public int getStatusBarPlaceColor() {
        return ContextCompat.getColor(activity, R.color.colorPrimaryDark);
    }

    @Override
    public boolean isShowStatusBarPlaceColor() {
        return true;
    }

    @Override
    public boolean isWindowSetting() {
        return true;
    }

    @Override
    public boolean toggleOverridePendingTransition() {
        return true;
    }

    @Override
    public RKTransitionMode getOverridePendingTransitionMode() {
        return RKTransitionMode.RIGHT;
    }

}
