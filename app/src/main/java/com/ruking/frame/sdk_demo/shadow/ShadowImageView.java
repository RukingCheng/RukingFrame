/*
 * Copyright 2016 yinglan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ruking.frame.sdk_demo.shadow;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.ColorInt;
import android.support.v7.graphics.Palette;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.zhy.autolayout.AutoLinearLayout;

/**
 * ================================================
 * 作    者：sufly0001@gmail.com
 * 版    本：1.1
 * 创建日期 ：2017/04/01
 * 描    述：
 * 修订历史： 修改显示
 * ================================================
 */
public class ShadowImageView extends AutoLinearLayout {

    private int shadowRound = 0;
    private int shadowColor = -147483648;
    private boolean mInvalidat;

    public ShadowImageView(Context context) {
        this(context, null);
    }

    public ShadowImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShadowImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
//        setPadding(80, 40, 80, 120);
//        setGravity(Gravity.CENTER);
//        float density = context.getResources().getDisplayMetrics().density;
//        shadowRound = (int) (shadowRound * density);
//        if (this.shadowColor == Color.parseColor("#8D8D8D")) {
//            this.shadowColor = -147483648;
//        }
        mInvalidat = true;
//        setLayerType(View.LAYER_TYPE_SOFTWARE, null);  // 关闭硬件加速
//        this.setWillNotDraw(false);                    // 调用此方法后，才会执行 onDraw(Canvas) 方法
    }


    public void setImageShadowColor(@ColorInt int color) {
        this.shadowColor = color;
    }

    @Override
    public void draw(Canvas canvas) {
        if (mInvalidat) {
            mInvalidat = false;
            Paint shadowPaint = new Paint();
            shadowPaint.setColor(Color.WHITE);
            shadowPaint.setStyle(Paint.Style.FILL);
            shadowPaint.setAntiAlias(true);
            for (int i = 0; i < getChildCount(); i++) {
                View view = getChildAt(i);
//            view.setLayerType(LAYER_TYPE_SOFTWARE, null);
                int radius = view.getHeight() / 12 > 340 ? 340 : view.getHeight() / 12;
                int shadowDimen = view.getHeight() / 16 > 100 ? 100 : view.getHeight() / 16;
                Bitmap bitmap;
                int rgb;
                if (view instanceof ImageView && ((ImageView) view).getDrawable() instanceof
                        ColorDrawable) {
                    rgb = ((ColorDrawable) ((ImageView) view).getDrawable()).getColor();
                    shadowPaint.setShadowLayer(40, 0, 28, getDarkerColor(rgb));
                } else if (view instanceof ImageView && ((ImageView) view).getDrawable() instanceof BitmapDrawable) {
                    bitmap = ((BitmapDrawable) ((ImageView) view).getDrawable()).getBitmap();
                    Palette.Swatch mSwatch = Palette.from(bitmap).generate().getDominantSwatch();
                    if (null != mSwatch) {
                        rgb = mSwatch.getRgb();
                    } else {
                        rgb = Color.parseColor("#8D8D8D");
                    }
                    shadowPaint.setShadowLayer(radius, 0, shadowDimen, getDarkerColor(rgb));
                    Bitmap bitmapT = Bitmap.createBitmap(bitmap, 0, bitmap.getHeight() / 4 * 3,
                            bitmap.getWidth(), bitmap.getHeight() / 4);
                    if (null != Palette.from(bitmapT).generate().getDominantSwatch()) {
                        rgb = Palette.from(bitmapT).generate().getDominantSwatch().getRgb();
                        shadowPaint.setShadowLayer(radius, 0, shadowDimen, rgb);
                    }
                } else if (view.getBackground() instanceof
                        ColorDrawable) {
                    rgb = ((ColorDrawable) view.getBackground()).getColor();
                    shadowPaint.setShadowLayer(40, 0, 28, getDarkerColor(rgb));
                } else if (view.getBackground() instanceof BitmapDrawable) {
                    bitmap = ((BitmapDrawable) view.getBackground()).getBitmap();
                    Palette.Swatch mSwatch = Palette.from(bitmap).generate().getDominantSwatch();
                    if (null != mSwatch) {
                        rgb = mSwatch.getRgb();
                    } else {
                        rgb = Color.parseColor("#8D8D8D");
                    }
                    shadowPaint.setShadowLayer(radius, 0, shadowDimen, getDarkerColor(rgb));
                    Bitmap bitmapT = Bitmap.createBitmap(bitmap, 0, bitmap.getHeight() / 4 * 3,
                            bitmap.getWidth(), bitmap.getHeight() / 4);
                    if (null != Palette.from(bitmapT).generate().getDominantSwatch()) {
                        rgb = Palette.from(bitmapT).generate().getDominantSwatch().getRgb();
                        shadowPaint.setShadowLayer(radius, 0, shadowDimen, rgb);
                    }
                } else {
                    rgb = Color.parseColor("#8D8D8D");
                    shadowPaint.setShadowLayer(radius, 0, shadowDimen, getDarkerColor(rgb));
                }
//            if (this.shadowColor != -147483648) {
//            shadowPaint.setShadowLayer(radius, 0, shadowDimen, getDarkerColor(Color
//                    .parseColor("#8D8D8D")));
//            }
                RectF rectF = new RectF(view.getX() + (view.getWidth() / 20), view.getY(),
                        view.getX() + view.getWidth() - (view.getWidth() / 20),
                        view.getY() + view.getHeight() - ((view.getHeight() / 40)));
//                ViewCompat.setLayerPaint(this, shadowPaint);
                canvas.drawRoundRect(rectF, shadowRound, shadowRound, shadowPaint);
//            canvas.drawCircle(rectF.centerX(), rectF.centerY(), Math.min(rectF.width(), rectF.height())/ 2, shadowPaint);

            }
            canvas.save();
        }
        super.draw(canvas);
    }

    public int getDarkerColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[1] = hsv[1] + 0.1f;
        hsv[2] = hsv[2] - 0.1f;
        return Color.HSVToColor(hsv);
    }
}
