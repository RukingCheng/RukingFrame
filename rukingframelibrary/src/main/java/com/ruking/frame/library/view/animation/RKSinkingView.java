package com.ruking.frame.library.view.animation;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Typeface;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.View;

import com.ruking.frame.library.R;
import com.ruking.frame.library.view.animation.base.RKViewAnimationBase;
import com.zhy.autolayout.utils.AutoUtils;


/**
 * 自定义进度球
 *
 * @author 史伟成 E-mail:495095492@qq.com 电话：15216801944
 * @version 创建时间：2015-4-17 下午2:06:05
 */
public class RKSinkingView extends View {
    private RKViewAnimationBase mRKViewAnimationBase;
    private Paint mPaint = new Paint();// 画笔
    private long c = 0L;// 每次onDraw时c都会自增
    private int progress;// 进度
    private int maxProgress;//最大进度
    private int progressTextSize;//显示进度条字体大小
    private int progressColor;//进度条文字颜色
    private String companyText;//单位
    private int companyColor;//单位文字颜色
    private int companySize;//单位文字大小
    private String bottomText;//底部描述文字
    private int bottomTextColor;//底部描述文字颜色
    private int bottomTextSize;//底部描述文字大小
    private int upColor;//进度条上面的颜色
    private int downColor;//进度条下面颜色


    public RKSinkingView(Context context) {
        super(context);
        init(null);
    }

    public RKSinkingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public RKSinkingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attributeSet) {
        mRKViewAnimationBase = new RKViewAnimationBase(this, attributeSet);
        mPaint.setAntiAlias(true);
        TypedArray ta = getContext().obtainStyledAttributes(attributeSet, R.styleable.rkframe);
        progress = ta.getInteger(R.styleable.rkframe_progress, 0);// 进度
        maxProgress = ta.getInteger(R.styleable.rkframe_maxProgress, 100);//最大进度
        progressTextSize = ta.getDimensionPixelSize(R.styleable.rkframe_progressTextSize, 60);//显示进度条字体大小
        progressColor = ta.getColor(R.styleable.rkframe_progressColor, Color.WHITE);//进度条文字颜色
        companyText = ta.getString(R.styleable.rkframe_companyText);//单位
        companyColor = ta.getColor(R.styleable.rkframe_companyColor, progressColor);//单位文字颜色
        companySize = ta.getDimensionPixelSize(R.styleable.rkframe_companySize, progressTextSize / 3);//单位文字大小
        bottomText = ta.getString(R.styleable.rkframe_bottomText);//底部描述文字
        bottomTextColor = ta.getColor(R.styleable.rkframe_bottomTextColor, progressColor);//底部描述文字颜色
        bottomTextSize = ta.getDimensionPixelSize(R.styleable.rkframe_bottomTextSize, progressTextSize / 3);//底部描述文字大小
        upColor = ta.getColor(R.styleable.rkframe_upColor, Color.parseColor("#cc493d"));//进度条上面的颜色
        downColor = ta.getColor(R.styleable.rkframe_downColor, Color.parseColor("#ec5a42"));//进度条下面颜色
        ta.recycle();//为了保持以后使用的一致性，需要回收
        progressTextSize = AutoUtils.getPercentWidthSizeBigger(progressTextSize);
        companySize = AutoUtils.getPercentWidthSizeBigger(companySize);
        bottomTextSize = AutoUtils.getPercentWidthSizeBigger(bottomTextSize);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRKViewAnimationBase.onSizeChanged(w, h);
    }

    @Override
    public void draw(Canvas canvas) {
        mRKViewAnimationBase.draw(canvas);
        super.draw(canvas);
        int width = getWidth();
        int height = getHeight();
        // 如果未开始（未调用startWave方法）,绘制一个矩形
        if ((width == 0) || (height == 0)) {
            canvas.drawRect(0.0F, height / 2, width, height, mPaint);
            return;
        }
        canvas.restore();
        canvas.save();
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.FILTER_BITMAP_FLAG | Paint.ANTI_ALIAS_FLAG));
        canvas.clipPath(mRKViewAnimationBase.getmClipPath());
        if (c >= 8388607L) {
            c = 0L;
        } // 每次onDraw时c都会自增
        c = (1L + c);
        float mPercent = (float) progress / (float) maxProgress;
        float f1 = height * (1.0F - mPercent);
        float amplitude = 20.0F;
        int top = (int) (f1 + amplitude);
        mPaint.setColor(upColor);// 设置上边背景
        mPaint.setStyle(Style.FILL);
        canvas.drawRect(0, 0, width, f1 + amplitude, mPaint);// 正方形
        mPaint.setStrokeWidth(1.0F);
        mPaint.setColor(downColor);// 设置波浪背景
        canvas.drawRect(0.0F, top, width, height, mPaint);
        int startX = 0; // 波浪效果
        while (startX < width) {
            float f = 0.033F;
            int startY = (int) (f1 - amplitude
                    * Math.sin(Math.PI * (2.0F * (startX + c * width * f))
                    / width));
            canvas.drawLine(startX, startY, startX, top, mPaint);
            startY = (int) (f1 + amplitude
                    / 2
                    * Math.sin(Math.PI * (2.0F * (startX + c * width * f))
                    / width));
            canvas.drawLine(startX, startY, startX, top, mPaint);
            startX++;
        }
        canvas.drawRect(0, f1 + amplitude, width, height, mPaint);// 正方形
        // 进度
        String str = (int) (mPercent * 100) + "";
        mPaint.setColor(progressColor);
        mPaint.setTextSize(progressTextSize);
        mPaint.setStyle(Style.FILL_AND_STROKE);
        mPaint.setTypeface(Typeface.create(Typeface.DEFAULT_BOLD,
                Typeface.BOLD));
        float ii = mPaint.measureText(str);
        canvas.drawText(str, (getWidth() - ii) / 2, getHeight() / 2
                + progressTextSize / 2 - progressTextSize / 4, mPaint);
        mPaint.setTypeface(Typeface.DEFAULT);
        //单位
        if (companyText != null && !companyText.equals("")) {
            mPaint.setTextSize(companySize);
            mPaint.setColor(companyColor);
            mPaint.setStyle(Style.FILL);
            canvas.drawText(companyText, (getWidth() + ii) / 2, getHeight() / 2
                    - companySize, mPaint);
        }
        //底部文字
        if (bottomText != null && !bottomText.equals("")) {
            mPaint.setTextSize(bottomTextSize);
            mPaint.setColor(bottomTextColor);
            mPaint.setStyle(Style.FILL);
            canvas.drawText(bottomText,
                    (getWidth() - mPaint.measureText(bottomText)) / 2,
                    getHeight() / 2 + progressTextSize - bottomTextSize, mPaint);
        }
        postInvalidateDelayed(20);
        mRKViewAnimationBase.drawOff(canvas);
    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
    }

    public void setProgressTextSize(int progressTextSize) {
        this.progressTextSize = AutoUtils.getPercentWidthSizeBigger(progressTextSize);
    }

    public void setTextSize(int progressTextSize) {
        this.progressTextSize = AutoUtils.getPercentWidthSizeBigger(progressTextSize);
        this.companySize = AutoUtils.getPercentWidthSizeBigger(progressTextSize / 3);
        this.bottomTextSize = AutoUtils.getPercentWidthSizeBigger(progressTextSize / 3);
    }

    public RKViewAnimationBase getRKViewAnimationBase() {
        return mRKViewAnimationBase;
    }

    public void setProgressColor(@ColorInt int progressColor) {
        this.progressColor = progressColor;
    }

    public void setCompanyText(@NonNull String companyText) {
        this.companyText = companyText;
    }

    public void setCompanyText(@StringRes int companyText) {
        this.companyText = getResources().getString(companyText);
    }

    public void setCompanyColor(@ColorInt int companyColor) {
        this.companyColor = companyColor;
    }

    public void setCompanySize(int companySize) {
        this.companySize = AutoUtils.getPercentWidthSizeBigger(companySize);
    }

    public void setBottomText(@NonNull String bottomText) {
        this.bottomText = bottomText;
    }

    public void setBottomText(@StringRes int bottomText) {
        this.bottomText = getResources().getString(bottomText);
    }

    public void setBottomTextColor(@ColorInt int bottomTextColor) {
        this.bottomTextColor = bottomTextColor;
    }

    public void setBottomTextSize(int bottomTextSize) {
        this.bottomTextSize = AutoUtils.getPercentWidthSizeBigger(bottomTextSize);
    }

    public void setUpColor(@ColorInt int upColor) {
        this.upColor = upColor;
    }

    public void setDownColor(@ColorInt int downColor) {
        this.downColor = downColor;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        postInvalidate();
    }

    public int getProgress() {
        return progress;
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    public void setPercentThread(int percent) {
        Thread thread = new Thread(() -> {
            progress = 0;
            while (progress <= percent) {
                progress += 1f;
                try {
                    Thread.sleep(40);
                } catch (InterruptedException ignored) {
                }
            }
            progress = percent;
        });
        thread.start();
        postInvalidate();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow(); // 关闭硬件加速，防止异常
        try {
            this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        } catch (Exception ignored) {
        }
    }
}
