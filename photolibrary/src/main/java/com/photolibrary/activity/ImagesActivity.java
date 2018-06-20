package com.photolibrary.activity;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.photolibrary.PictureSelectionCache;
import com.photolibrary.R;
import com.photolibrary.bean.ImageAttr;
import com.photolibrary.util.ColorUtil;
import com.ruking.frame.library.rxbus.RxBus;
import com.ruking.frame.library.utils.RKWindowUtil;
import com.ruking.frame.library.view.ToastUtil;
import com.ruking.frame.library.view.animation.RKAnimationButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImagesActivity extends AppCompatActivity {

    public static final String IMAGE_ATTR = "image_attr";
    public static final String CUR_POSITION = "cur_position";
    public static final String TYPE = "type";
    public static final int ANIM_DURATION = 300; // ms

    private RelativeLayout rootView;
    private TextView tvTip;
    private ViewPager viewPager;
    private List<ImageAttr> imageAttrs;
    private int curPosition;
    private int screenWidth;
    private int screenHeight;
    private float scaleX;
    private float scaleY;
    private float translationX;
    private float translationY;
    private int type;//0:只是展示图片，1，从本地图片列表来，2：需要显示选择按钮

    private ImageView imgeChoose;
    private RKAnimationButton albumBut;

    public static void starUrlsActivity(@NonNull Activity activity, @NonNull String...
            images) {
        starUrlsActivity(activity, null, images);
    }

    public static void starUrlsActivity(@NonNull Activity activity, View view, @NonNull String...
            images) {
        starUrlsActivity(activity, Arrays.asList(images), view, 0, 0);
    }

    public static void starUrlsActivity(@NonNull Activity activity, @NonNull List<String>
            images, int index) {
        starUrlsActivity(activity, images, index, 0);
    }

    public static void starUrlsActivity(@NonNull Activity activity, @NonNull List<String>
            images, int index, int type) {
        starUrlsActivity(activity, images, null, index, type);

    }

    public static void starUrlsActivity(@NonNull Activity activity, @NonNull List<String>
            images, View view, int index) {
        starUrlsActivity(activity, images, view, index, 0);
    }

    /**
     * @param activity activity
     * @param images   图片列表
     * @param view     弹出开始的位置
     * @param index    选择第几张
     * @param type     类别
     */
    public static void starUrlsActivity(@NonNull Activity activity, @NonNull List<String>
            images, View view, int index, int type) {
        List<ImageAttr> images02 = new ArrayList<>();
        for (int i = 0; i < images.size(); i++) {
            ImageAttr attr = new ImageAttr();
            attr.url = images.get(i);
            if (view != null) {
                attr.width = view.getWidth();
                attr.height = view.getHeight();
                int[] points = new int[2];
                view.getLocationInWindow(points);
                attr.left = points[0];
                attr.top = points[1];
            }
            images02.add(attr);
        }
        starImageAttrsActivity(activity, images02, index, type);
    }

    public static void starImageAttrsActivity(@NonNull Activity activity, @NonNull List<ImageAttr>
            images, int index) {
        starImageAttrsActivity(activity, images, index, 0);
    }

    public static void starImageAttrsActivity(@NonNull Activity activity, @NonNull List<ImageAttr>
            images, int index, int type) {
        starImageAttrsActivity(activity, images, index, type, PictureSelectionCache.PICTURE_SELECTION_CACHE);
    }

    public static void starImageAttrsActivity(@NonNull Activity activity, @NonNull List<ImageAttr>
            images, int index, int type, int what) {
        Intent intent = new Intent(activity, ImagesActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(ImagesActivity.IMAGE_ATTR, (Serializable) images);
        bundle.putInt(ImagesActivity.CUR_POSITION, index);
        bundle.putInt(ImagesActivity.TYPE, type);
        bundle.putInt("what", what);
        intent.putExtras(bundle);
        activity.startActivity(intent);
        activity.overridePendingTransition(0, 0);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        RKWindowUtil.windowSetting(this);
        tvTip = findViewById(R.id.tv_tip);
        rootView = findViewById(R.id.rootView);
        imgeChoose = findViewById(R.id.imge_choose);
        albumBut = findViewById(R.id.album_but);
        screenWidth = RKWindowUtil.getScreenWidth(this);
        screenHeight = RKWindowUtil.getScreenHeight(this);
        Intent intent = getIntent();
        imageAttrs = (List<ImageAttr>) intent.getSerializableExtra(IMAGE_ATTR);
        curPosition = intent.getIntExtra(CUR_POSITION, 0);
        type = intent.getIntExtra(TYPE, 0);
        tvTip.setText(String.format(getString(R.string.image_index), (curPosition + 1), imageAttrs.size()));
        viewPager = findViewById(R.id.viewPager);
        TestFragmentAdapter adapter = new TestFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(curPosition);
        viewPager.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                findViewById(R.id.layout).setVisibility(View.GONE);
                rootView.getViewTreeObserver().removeOnPreDrawListener(this);
                ImageAttr attr = imageAttrs.get(curPosition);
                initImageAttr(attr);
                setBackgroundColor(0f, 1f, new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        findViewById(R.id.layout).setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
                translateXAnim(viewPager, translationX, 0);
                translateYAnim(viewPager, translationY, 0);
                scaleXAnim(viewPager, scaleX, 1);
                scaleYAnim(viewPager, scaleY, 1);
                return true;
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                curPosition = position;
                tvTip.setText(String.format(getString(R.string.image_index), (curPosition + 1), imageAttrs.size()));
                if (type != 0) showImgeChoose();
            }
        });
        if (type == 2) {
            for (ImageAttr attr : imageAttrs)
                PictureSelectionCache.tempSelectBitmap.put(attr.url, attr);
        }
        if (type == 0) {
            imgeChoose.setVisibility(View.GONE);
            albumBut.setVisibility(View.GONE);
            findViewById(R.id.layout).setBackgroundColor(ContextCompat.getColor(this,
                    android.R.color.transparent));
        } else {
            imgeChoose.setVisibility(View.VISIBLE);
            albumBut.setVisibility(View.VISIBLE);
            showImgeChoose();
            imgeChoose.setOnClickListener(view -> {
                ImageAttr attr = imageAttrs.get(curPosition);
                if (PictureSelectionCache.tempSelectBitmap.containsKey(attr.url)) {
                    attr.isSelected = false;
                    PictureSelectionCache.tempSelectBitmap.remove(attr.url);
                } else {
                    if (PictureSelectionCache.tempSelectBitmap.size() >= PictureSelectionCache.num) {
                        ToastUtil.show(ImagesActivity.this, R.string.only_choose_num);
                        return;
                    }
                    attr.isSelected = true;
                    PictureSelectionCache.tempSelectBitmap.put(attr.url, attr);
                }
                showImgeChoose();
                if (PictureSelectionCache.tempSelectBitmap.size() <= 0) {
                    albumBut.setText(R.string.album_complete);
                } else
                    albumBut.setText(getString(R.string.album_complete) + "(" + PictureSelectionCache
                            .tempSelectBitmap.size() + "/" + PictureSelectionCache.num + ")");
            });
            if (PictureSelectionCache.tempSelectBitmap.size() <= 0) {
                albumBut.setText(R.string.album_complete);
            } else
                albumBut.setText(getString(R.string.album_complete) + "(" + PictureSelectionCache
                        .tempSelectBitmap.size() + "/" + PictureSelectionCache.num + ")");
            albumBut.setOnClickListener(view -> {
                if (PictureSelectionCache.tempSelectBitmap.size() <= 0) {
                    //ToastUtil.show(ImagesActivity.this, R.string.album_err);
                    //return;
                    ImageAttr attr = imageAttrs.get(curPosition);
                    attr.isSelected = true;
                    PictureSelectionCache.tempSelectBitmap.put(attr.url, attr);
                    showImgeChoose();
                    albumBut.setText(getString(R.string.album_complete) + "(" + PictureSelectionCache
                            .tempSelectBitmap.size() + "/" + PictureSelectionCache.num + ")");
                }
                Message message = new Message();
                message.what = intent.getIntExtra("what", PictureSelectionCache.PICTURE_SELECTION_CACHE);
                message.obj = PictureSelectionCache.getImagetAttr();
                RxBus.getDefault().post(message);
                PictureSelectionCache.clear();
                finish();
            });
        }
    }

    private void showImgeChoose() {
        if (PictureSelectionCache.tempSelectBitmap.containsKey(imageAttrs.get(curPosition).url)) {
            imgeChoose.setImageResource(R.mipmap.rk_choose_normal);
            imgeChoose.setColorFilter(ContextCompat.getColor(this, R.color.colorAccent));
        } else {
            imgeChoose.setImageResource(R.mipmap.rk_choose_checked);
            imgeChoose.setColorFilter(null);
        }
    }


    private class TestFragmentAdapter extends FragmentPagerAdapter {

        TestFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            ImagesFragment fragment = new ImagesFragment();
            Bundle args = new Bundle();
            args.putSerializable("data", imageAttrs.get(position));
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return imageAttrs.size();
        }
    }

    @Override
    public void onBackPressed() {
        finishWithAnim();
    }

    private void initImageAttr(ImageAttr attr) {
        int originalWidth = attr.width;
        int originalHeight = attr.height;
        int originalCenterX = attr.left + originalWidth / 2;
        int originalCenterY = attr.top + originalHeight / 2;
        float widthRatio = screenWidth * 1.0f / originalWidth;
        float heightRatio = screenHeight * 1.0f / originalHeight;
        float ratio = widthRatio > heightRatio ? heightRatio : widthRatio;
        int finalWidth = (int) (originalWidth * ratio);
        int finalHeight = (int) (originalHeight * ratio);
        scaleX = originalWidth * 1.0f / finalWidth;
        scaleY = originalHeight * 1.0f / finalHeight;
        translationX = originalCenterX - screenWidth / 2;
        translationY = originalCenterY - screenHeight / 2;
    }


    public void finishWithAnim() {
        findViewById(R.id.layout).setVisibility(View.GONE);
        ImageAttr attr = imageAttrs.get(curPosition);
        initImageAttr(attr);
        translateXAnim(viewPager, 0, translationX);
        translateYAnim(viewPager, 0, translationY);
        scaleXAnim(viewPager, 1, scaleX);
        scaleYAnim(viewPager, 1, scaleY);
        setBackgroundColor(1f, 0f, new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                finish();
                if (type == 2) {
                    PictureSelectionCache.clear();
                }
                overridePendingTransition(0, 0);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
    }

    private void translateXAnim(final ViewPager photoView, float from, float to) {
        ValueAnimator anim = ValueAnimator.ofFloat(from, to);
        anim.addUpdateListener(valueAnimator -> photoView.setX((Float) valueAnimator.getAnimatedValue()));
        anim.setDuration(ANIM_DURATION);
        anim.start();
    }

    private void translateYAnim(final ViewPager photoView, float from, float to) {
        ValueAnimator anim = ValueAnimator.ofFloat(from, to);
        anim.addUpdateListener(valueAnimator -> photoView.setY((Float) valueAnimator.getAnimatedValue()));
        anim.setDuration(ANIM_DURATION);
        anim.start();
    }

    private void scaleXAnim(final ViewPager photoView, float from, float to) {
        ValueAnimator anim = ValueAnimator.ofFloat(from, to);
        anim.addUpdateListener(valueAnimator -> photoView.setScaleX((Float) valueAnimator.getAnimatedValue()));
        anim.setDuration(ANIM_DURATION);
        anim.start();
    }

    private void scaleYAnim(final ViewPager photoView, float from, float to) {
        ValueAnimator anim = ValueAnimator.ofFloat(from, to);
        anim.addUpdateListener(valueAnimator -> photoView.setScaleY((Float) valueAnimator.getAnimatedValue()));
        anim.setDuration(ANIM_DURATION);
        anim.start();
    }

    private void setBackgroundColor(float from, float to, Animator.AnimatorListener listener) {
        ValueAnimator anim = ValueAnimator.ofFloat(from, to);
        anim.addUpdateListener(valueAnimator -> rootView.setBackgroundColor(ColorUtil.evaluate((Float) valueAnimator.getAnimatedValue(), Color.TRANSPARENT, Color.BLACK)));
        anim.setDuration(ANIM_DURATION);
        if (listener != null) {
            anim.addListener(listener);
        }
        anim.start();
    }
}
