package com.acker.simplezxing.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.acker.simplezxing.R;
import com.acker.simplezxing.camera.CameraManager;
import com.google.zxing.ResultPoint;

import java.util.ArrayList;
import java.util.List;

public final class ViewfinderView extends View {

    private static final int[] SCANNER_ALPHA = {128, 64, 128, 192, 255, 192, 128, 64};
    private static final long ANIMATION_DELAY = 100L;
    private static final int CURRENT_POINT_OPACITY = 0xA0;
    private static final int MAX_RESULT_POINTS = 20;
    private static final int POINT_SIZE = 6;
    private final Paint paint;
    private final int maskColor;
    private final int resultColor;
    private final int laserColor;
    private final int resultPointColor;
    private CameraManager cameraManager;
    private Bitmap resultBitmap;
    private int scannerAlpha;
    private List<ResultPoint> possibleResultPoints;
    private List<ResultPoint> lastPossibleResultPoints;
    private boolean needDrawText;
    private boolean fullScreen;
    private int scannerXY;

    // This constructor is used when the class is built from an XML resource.
    public ViewfinderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        maskColor = Color.parseColor("#60000000");
        resultColor = Color.parseColor("#e0000000");
        laserColor = ContextCompat.getColor(getContext(), R.color.viewfinder_laser);
        resultPointColor = Color.parseColor("#c0ffff00");
        scannerAlpha = 0;
        possibleResultPoints = new ArrayList<>(5);
        lastPossibleResultPoints = null;
    }

    public void setCameraManager(CameraManager cameraManager) {
        this.cameraManager = cameraManager;
    }

    public void setNeedDrawText(boolean needDrawText) {
        this.needDrawText = needDrawText;
    }

    public void setScanAreaFullScreen(boolean fullScreen) {
        this.fullScreen = fullScreen;
    }

    @SuppressLint("DrawAllocation")
    @Override
    public void onDraw(Canvas canvas) {
        if (cameraManager == null) {
            return;
        }
        Rect frame = cameraManager.getFramingRect();
        Rect previewFrame = cameraManager.getFramingRectInPreview();
        if (frame == null || previewFrame == null) {
            return;
        }
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        int w = Math.round(
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics()));
        int w2 = Math.round(
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics()));
        int w3 = Math.round(
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics()));
        int h = Math.round(
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics()));
        // Draw the exterior (i.e. outside the framing rect) darkened
        paint.setColor(resultBitmap != null ? resultColor : maskColor);
        canvas.drawRect(0, 0, width, frame.top, paint);
        canvas.drawRect(0, frame.top, frame.left, frame.bottom + w2, paint);
        canvas.drawRect(frame.right + w2, frame.top, width, frame.bottom + w2, paint);
        canvas.drawRect(0, frame.bottom + w2, width, height, paint);
        // Draw four corner
        paint.setColor(laserColor);
        canvas.drawRect(frame.left - w, frame.top - w, frame.left, frame.top + h, paint);
        canvas.drawRect(frame.left, frame.top - w, frame.left + h, frame.top, paint);
        canvas.drawRect(frame.right, frame.top - w, frame.right + w, frame.top + h, paint);
        canvas.drawRect(frame.right - h, frame.top - w, frame.right, frame.top, paint);
        canvas.drawRect(frame.left - w, frame.bottom - h, frame.left, frame.bottom + w, paint);
        canvas.drawRect(frame.left, frame.bottom, frame.left + h, frame.bottom + w, paint);
        canvas.drawRect(frame.right, frame.bottom - h, frame.right + w, frame.bottom + w, paint);
        canvas.drawRect(frame.right - h, frame.bottom, frame.right, frame.bottom + w, paint);
        paint.setAlpha(CURRENT_POINT_OPACITY);
        canvas.drawLine(frame.left, frame.top, frame.right, frame.top, paint);
        canvas.drawLine(frame.left, frame.bottom, frame.right, frame.bottom, paint);
        canvas.drawLine(frame.left, frame.top, frame.left, frame.bottom, paint);
        canvas.drawLine(frame.right, frame.top, frame.right, frame.bottom, paint);
        if (resultBitmap != null) {
            // Draw the opaque result bitmap over the scanning rectangle
            paint.setAlpha(CURRENT_POINT_OPACITY);
            canvas.drawBitmap(resultBitmap, null, frame, paint);
        } else {
            // Draw a red "laser scanner" line through the middle to show decoding is active
//            paint.setColor(Color.RED);
//            paint.setAlpha(SCANNER_ALPHA[scannerAlpha]);
//            scannerAlpha = (scannerAlpha + 1) % SCANNER_ALPHA.length;
//            int middle = frame.height() / 2 + frame.top;
//            canvas.drawRect(frame.left + 2, middle - 1, frame.right - 1, middle + 2, paint);
            paint.setColor(laserColor);
            paint.setAlpha(SCANNER_ALPHA[scannerAlpha]);
            scannerAlpha = (scannerAlpha + 1) % SCANNER_ALPHA.length;
            scannerXY = scannerXY + w3;
            if (scannerXY < frame.top || scannerXY + w3 > frame.bottom) {
                scannerXY = frame.top;
            }
            RectF oval = new RectF(frame.left + w3, scannerXY, frame.right - w3, scannerXY + w3);// 设置个新的长方形，扫描测量
            canvas.drawOval(oval, paint);
            float scaleX = frame.width() / (float) previewFrame.width();
            float scaleY = frame.height() / (float) previewFrame.height();
            List<ResultPoint> currentPossible = possibleResultPoints;
            List<ResultPoint> currentLast = lastPossibleResultPoints;
            int frameLeft = frame.left;
            int frameTop = frame.top;
            if (currentPossible.isEmpty()) {
                lastPossibleResultPoints = null;
            } else {
                possibleResultPoints = new ArrayList<>(5);
                lastPossibleResultPoints = currentPossible;
                paint.setAlpha(CURRENT_POINT_OPACITY);
                paint.setColor(resultPointColor);
                synchronized (currentPossible) {
                    for (ResultPoint point : currentPossible) {
                        if (fullScreen)
                            canvas.drawCircle((int) (point.getX() * scaleX),
                                    (int) (point.getY() * scaleY),
                                    POINT_SIZE, paint);
                        else
                            canvas.drawCircle(frameLeft + (int) (point.getX() * scaleX),
                                    frameTop + (int) (point.getY() * scaleY),
                                    POINT_SIZE, paint);
                    }
                }
            }
            if (currentLast != null) {
                paint.setAlpha(CURRENT_POINT_OPACITY / 2);
                paint.setColor(resultPointColor);
                synchronized (currentLast) {
                    float radius = POINT_SIZE / 2.0f;
                    for (ResultPoint point : currentLast) {
                        if (fullScreen)
                            canvas.drawCircle((int) (point.getX() * scaleX),
                                    (int) (point.getY() * scaleY),
                                    radius, paint);
                        else
                            canvas.drawCircle(frameLeft + (int) (point.getX() * scaleX),
                                    frameTop + (int) (point.getY() * scaleY),
                                    radius, paint);
                    }
                }
            }

            if (needDrawText) {
                int textSize = Math.round(
                        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 14, getResources().getDisplayMetrics()));
                TextPaint paint = new TextPaint();
                paint.setColor(Color.WHITE);
                paint.setTextSize(textSize);
                StaticLayout layout = new StaticLayout(getResources().getString(R.string.hint_scan), paint, width, Layout.Alignment.ALIGN_CENTER, 1.0F, 0.0F, false);
                canvas.translate(0, frame.bottom + textSize);
                layout.draw(canvas);
            }
            // Request another update at the animation interval, but only repaint the laser line,
            // not the entire viewfinder mask.
            postInvalidateDelayed(ANIMATION_DELAY,
                    frame.left - POINT_SIZE,
                    frame.top - POINT_SIZE,
                    frame.right + POINT_SIZE,
                    frame.bottom + POINT_SIZE);
        }


    }

    public void drawViewfinder() {
        Bitmap resultBitmap = this.resultBitmap;
        this.resultBitmap = null;
        if (resultBitmap != null) {
            resultBitmap.recycle();
        }
        invalidate();
    }


    public void addPossibleResultPoint(ResultPoint point) {
        List<ResultPoint> points = possibleResultPoints;
        synchronized (points) {
            points.add(point);
            int size = points.size();
            if (size > MAX_RESULT_POINTS) {
                // trim it
                points.subList(0, size - MAX_RESULT_POINTS / 2).clear();
            }
        }
    }

}
