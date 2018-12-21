package com.ruking.frame.library.view.animation.base;

import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Region;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import com.ruking.frame.library.R;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * 圆角边框，点击放大缩小操作类
 *
 * @author 史伟成 E-mail:495095492@qq.com 电话：18075121944
 * @time 创建时间：2017/5/5 14:37
 */

public class RKViewAnimationBase {
    private View view;
    private GestureDetector mGestureDetector = null;
    private boolean isDown = false, isEnd = false;
    private float[] radii = new float[8];   // top-left, top-right, bottom-right, bottom-left
    private Path mClipPath;                 // 剪裁区域路径
    private Paint mPaint;                   // 画笔
    private boolean mRoundAsCircle = false; // 圆形
    private int mStrokeColor;               // 描边颜色
    private int mStrokeWidth;               // 描边半径
    private Region mAreaRegion;             // 内容区域
    private boolean isAnimationEffect = true;//是否点击执行动画
    private boolean onclickable = true;       //是否可以点击

    private Paint roundPaint;
    private Paint imagePaint;

    public RKViewAnimationBase(View view, AttributeSet attributeSet) {
        this.view = view;
        TypedArray ta = view.getContext().obtainStyledAttributes(attributeSet, R.styleable.rkframe);
        mRoundAsCircle = ta.getBoolean(R.styleable.rkframe_round_as_circle, false);
        isAnimationEffect = ta.getBoolean(R.styleable.rkframe_animati_oneffect, true);
        onclickable = ta.getBoolean(R.styleable.rkframe_onclickable, true);
        mStrokeColor = ta.getColor(R.styleable.rkframe_stroke_color, Color.WHITE);
        mStrokeWidth = ta.getDimensionPixelSize(R.styleable.rkframe_stroke_width, 0);
        mStrokeWidth = AutoUtils.getPercentWidthSizeBigger(mStrokeWidth);
        int roundCorner = ta.getDimensionPixelSize(R.styleable.rkframe_round_corner, 0);
        int roundCornerTopLeft = ta.getDimensionPixelSize(
                R.styleable.rkframe_round_corner_top_left, roundCorner);
        roundCornerTopLeft = AutoUtils.getPercentWidthSizeBigger(roundCornerTopLeft);
        int roundCornerTopRight = ta.getDimensionPixelSize(
                R.styleable.rkframe_round_corner_top_right, roundCorner);
        roundCornerTopRight = AutoUtils.getPercentWidthSizeBigger(roundCornerTopRight);
        int roundCornerBottomLeft = ta.getDimensionPixelSize(
                R.styleable.rkframe_round_corner_bottom_left, roundCorner);
        roundCornerBottomLeft = AutoUtils.getPercentWidthSizeBigger(roundCornerBottomLeft);
        int roundCornerBottomRight = ta.getDimensionPixelSize(
                R.styleable.rkframe_round_corner_bottom_right, roundCorner);
        roundCornerBottomRight = AutoUtils.getPercentWidthSizeBigger(roundCornerBottomRight);
        radii[0] = roundCornerTopLeft;
        radii[1] = roundCornerTopLeft;
        radii[2] = roundCornerTopRight;
        radii[3] = roundCornerTopRight;
        radii[4] = roundCornerBottomRight;
        radii[5] = roundCornerBottomRight;
        radii[6] = roundCornerBottomLeft;
        radii[7] = roundCornerBottomLeft;
        mClipPath = new Path();
        mAreaRegion = new Region();
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);
        ta.recycle();//为了保持以后使用的一致性，需要回收
        view.setOnLongClickListener(view1 -> true);
        roundPaint = new Paint();
        roundPaint.setColor(Color.WHITE);
        roundPaint.setAntiAlias(true);
        roundPaint.setStyle(Paint.Style.FILL);
        roundPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        imagePaint = new Paint();
        imagePaint.setXfermode(null);
    }

    public void onSizeChanged(int w, int h) {
        RectF areas = new RectF();
        areas.left = 0;
        areas.top = 0;
        areas.right = w;
        areas.bottom = h;
        mClipPath.reset();
        if (mRoundAsCircle) {
            float d = areas.width() >= areas.height() ? areas.height() : areas.width();
            float r = d / 2;
            PointF center = new PointF(w / 2, h / 2);
            mClipPath.addCircle(center.x, center.y, r, Path.Direction.CW);
            int mEdgeFix = 10;
            mClipPath.moveTo(-mEdgeFix, -mEdgeFix);  // 通过空操作让Path区域占满画布
            mClipPath.moveTo(w + mEdgeFix, h + mEdgeFix);
        } else {
            mClipPath.addRoundRect(areas, radii, Path.Direction.CW);
        }
        Region clip = new Region((int) areas.left, (int) areas.top,
                (int) areas.right, (int) areas.bottom);
        mAreaRegion.setPath(mClipPath, clip);
    }

    public void draw(Canvas canvas) {
        canvas.saveLayer(new RectF(0, 0, canvas.getWidth(), canvas.getHeight()), imagePaint, Canvas
                .ALL_SAVE_FLAG);
    }

    public void drawOff(Canvas canvas) {
        if (mStrokeWidth > 0) {
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
            mPaint.setStrokeWidth(mStrokeWidth * 2);
            mPaint.setColor(mStrokeColor);
            mPaint.setStyle(Paint.Style.STROKE);
            Path path = new Path();
            path.moveTo(0, 0);
            path.lineTo(view.getWidth(), 0);
            path.lineTo(view.getWidth(), view.getHeight());
            path.lineTo(0, view.getHeight());
            path.lineTo(0, 0);
            canvas.drawPath(path, mPaint);
        }
        if (mRoundAsCircle) {
            float d = view.getWidth() >= view.getHeight() ? view.getHeight() : view.getWidth();
            float r = d / 2;
            drawTopLeft(canvas, r);
            drawTopRight(canvas, r);
            drawBottomLeft(canvas, r);
            drawBottomRight(canvas, r);
        } else {
            drawTopLeft(canvas, radii[0]);
            drawTopRight(canvas, radii[2]);
            drawBottomLeft(canvas, radii[6]);
            drawBottomRight(canvas, radii[4]);
        }
        canvas.restore();
    }

    private void drawTopLeft(Canvas canvas, float r) {
        if (r > 0) {
            Path path = new Path();
            path.moveTo(0, r);
            path.lineTo(0, 0);
            path.lineTo(r, 0);
            path.arcTo(new RectF(0, 0, r * 2, r * 2),
                    -90, -90);
            path.close();
            if (mStrokeWidth > 0) {
                canvas.drawPath(path, mPaint);
            }
            canvas.drawPath(path, roundPaint);
        }
    }

    private void drawTopRight(Canvas canvas, float r) {
        if (r > 0) {
            int width = view.getWidth();
            Path path = new Path();
            path.moveTo(width - r, 0);
            path.lineTo(width, 0);
            path.lineTo(width, r);
            path.arcTo(new RectF(width - 2 * r, 0, width,
                    r * 2), 0, -90);
            path.close();
            if (mStrokeWidth > 0) {
                canvas.drawPath(path, mPaint);
            }
            canvas.drawPath(path, roundPaint);
        }
    }

    private void drawBottomLeft(Canvas canvas, float r) {
        if (r > 0) {
            int height = view.getHeight();
            Path path = new Path();
            path.moveTo(0, height - r);
            path.lineTo(0, height);
            path.lineTo(r, height);
            path.arcTo(new RectF(0, height - 2 * r,
                    r * 2, height), 90, 90);
            path.close();
            if (mStrokeWidth > 0) {
                canvas.drawPath(path, mPaint);
            }
            canvas.drawPath(path, roundPaint);
        }
    }

    private void drawBottomRight(Canvas canvas, float r) {
        if (r > 0) {
            int height = view.getHeight();
            int width = view.getWidth();
            Path path = new Path();
            path.moveTo(width - r, height);
            path.lineTo(width, height);
            path.lineTo(width, height - r);
            path.arcTo(new RectF(width - 2 * r, height - 2
                    * r, width, height), 0, 90);
            path.close();
            if (mStrokeWidth > 0) {
                canvas.drawPath(path, mPaint);
            }
            canvas.drawPath(path, roundPaint);
        }
    }

    public void onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            isEnd = false;
        }
        if (mGestureDetector == null) {
            mGestureDetector = new GestureDetector(view.getContext(), new GestureDetector.OnGestureListener() {
                @Override
                public boolean onDown(MotionEvent e) {
                    startAnimation();
                    return true;
                }

                @Override
                public void onShowPress(MotionEvent e) {
                }

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    clearAnimation();
                    return true;
                }

                @Override
                public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                    return false;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    clearAnimation();
                }

                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                    clearAnimation();
                    return true;
                }
            });
        }
        if (!mAreaRegion.contains((int) ev.getX(), (int) ev.getY())) {
            clearAnimation();
        } else if (!isEnd) {
            mGestureDetector.onTouchEvent(ev);
        }
    }

    /**
     * 开始执行缩小动画
     */
    private void startAnimation() {
        ScaleAnimation acaleAnimation = new ScaleAnimation(1.0f, getF(), 1.0f, getF(), Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        acaleAnimation.setDuration(100);
        acaleAnimation.setFillAfter(true);
        if (isAnimationEffect)
            view.startAnimation(acaleAnimation);
        isDown = true;
    }

    /**
     * 清除之前缩小动画将其动画还原
     */
    private void clearAnimation() {
        if (!isDown) return;
        isDown = false;
        isEnd = true;
        ScaleAnimation acaleAnimation = new ScaleAnimation(getF(), 1.0f, getF(), 1.0f, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        acaleAnimation.setDuration(100);
        if (isAnimationEffect)
            view.startAnimation(acaleAnimation);
    }


    public Path getmClipPath() {
        return mClipPath;
    }

    private float getF() {
        return 1.0f - (float) Math
                .round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f,
                        view.getResources().getDisplayMetrics()))
                / (float) view.getWidth();
    }

    public boolean isAnimationEffect() {
        return isAnimationEffect;
    }

    public void setAnimationEffect(boolean isAnimationEffect) {
        this.isAnimationEffect = isAnimationEffect;
    }

    public boolean isOnClickable() {
        return onclickable;
    }

    public void setOnClickable(boolean onclickable) {
        this.onclickable = onclickable;
    }

    public boolean isRoundAsCircle() {
        return mRoundAsCircle;
    }

    public void setRoundAsCircle(boolean mRoundAsCircle) {
        this.mRoundAsCircle = mRoundAsCircle;
    }

    public int getStrokeColor() {
        return mStrokeColor;
    }

    public void setStrokeColor(@ColorInt int mStrokeColor) {
        this.mStrokeColor = mStrokeColor;
        invalidate();
    }

    public int getStrokeWidth() {
        return mStrokeWidth;
    }

    public void setStrokeWidth(int mStrokeWidth) {
        this.mStrokeWidth = AutoUtils.getPercentWidthSizeBigger(mStrokeWidth);
        invalidate();
    }

    public float getRoundCornerTopLeft() {
        return radii[0];
    }

    public void setRoundCornerTopLeft(int roundCornerTopLeft) {
        roundCornerTopLeft = AutoUtils.getPercentWidthSizeBigger(roundCornerTopLeft);
        radii[0] = roundCornerTopLeft;
        radii[1] = roundCornerTopLeft;
        invalidate();
    }

    public void setRroundCorner(int roundCorner) {
        roundCorner = AutoUtils.getPercentWidthSizeBigger(roundCorner);
        radii[0] = roundCorner;
        radii[1] = roundCorner;
        radii[2] = roundCorner;
        radii[3] = roundCorner;
        radii[4] = roundCorner;
        radii[5] = roundCorner;
        radii[6] = roundCorner;
        radii[7] = roundCorner;
        invalidate();
    }

    public float getRoundCornerTopRight() {
        return radii[2];
    }

    public void setRoundCornerTopRight(int roundCornerTopRight) {
        roundCornerTopRight = AutoUtils.getPercentWidthSizeBigger(roundCornerTopRight);
        radii[2] = roundCornerTopRight;
        radii[3] = roundCornerTopRight;
        invalidate();
    }

    public float getRoundCornerBottomLeft() {
        return radii[6];
    }

    public void setRoundCornerBottomLeft(int roundCornerBottomLeft) {
        roundCornerBottomLeft = AutoUtils.getPercentWidthSizeBigger(roundCornerBottomLeft);
        radii[6] = roundCornerBottomLeft;
        radii[7] = roundCornerBottomLeft;
        invalidate();
    }

    public float getRoundCornerBottomRight() {
        return radii[4];
    }

    public void setRoundCornerBottomRight(int roundCornerBottomRight) {
        roundCornerBottomRight = AutoUtils.getPercentWidthSizeBigger(roundCornerBottomRight);
        radii[4] = roundCornerBottomRight;
        radii[5] = roundCornerBottomRight;
        invalidate();
    }

    private void invalidate() {
        view.invalidate();
    }
}
