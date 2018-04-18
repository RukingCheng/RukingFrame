package com.acker.simplezxing.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.acker.simplezxing.R;
import com.acker.simplezxing.assit.AmbientLightManager;
import com.acker.simplezxing.assit.BeepManager;
import com.acker.simplezxing.camera.CameraManager;
import com.acker.simplezxing.decode.CaptureActivityHandler;
import com.acker.simplezxing.decode.Decode;
import com.acker.simplezxing.view.ViewfinderView;
import com.google.zxing.Result;
import com.ruking.frame.library.base.RKBaseActivity;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public abstract class CaptureActivity extends RKBaseActivity implements SurfaceHolder.Callback, Decode {
    private CameraManager cameraManager;
    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private BeepManager beepManager;
    private AmbientLightManager ambientLightManager;
    private MyOrientationDetector myOrientationDetector;

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        super.setContentView(R.layout.capture);
        ambientLightManager = new AmbientLightManager(this);
        beepManager = new BeepManager(this, true, true);
        myOrientationDetector = new MyOrientationDetector(this);
        myOrientationDetector.setLastOrientation(getWindowManager().getDefaultDisplay().getRotation());
        initView();
    }

    @Override
    public int getStatusBarColor() {
        return 0;
    }

    @Override
    public int getStatusBarPlaceColor() {
        return 0;
    }

    @Override
    public boolean isShowStatusBarPlaceColor() {
        return false;
    }

    @Override
    public boolean isWindowSetting() {
        return true;
    }

    private void initView() {
        findViewById(R.id.sys_off).setOnClickListener(v -> onBackPressed());
    }


    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    @Override
    protected void onResume() {
        super.onResume();
        myOrientationDetector.enable();
        cameraManager = new CameraManager(getApplication(), true, true);
        viewfinderView = findViewById(R.id.viewfinder_view);
        viewfinderView.setCameraManager(cameraManager);
        viewfinderView.setNeedDrawText(true);
        viewfinderView.setScanAreaFullScreen(true);
        handler = null;
        beepManager.updatePrefs();
        if (ambientLightManager != null) {
            ambientLightManager.start(cameraManager);
        }
        SurfaceView surfaceView = findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
        }
    }

    @Override
    protected void onPause() {
        myOrientationDetector.disable();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        if (ambientLightManager != null) {
            ambientLightManager.stop();
        }
        beepManager.close();
        cameraManager.closeDriver();
        if (!hasSurface) {
            SurfaceView surfaceView = findViewById(R.id.preview_view);
            SurfaceHolder surfaceHolder = surfaceView.getHolder();
            surfaceHolder.removeCallback(this);
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                initCamera(holder);
            }
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    /**
     * A valid barcode has been found, so give an indication of success and show the results.
     *
     * @param rawResult The contents of the barcode.
     */
    public void handleDecode(Result rawResult) {
        beepManager.playBeepSoundAndVibrate();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //TODO
    }

    public void handleException(String e) {
        finish();
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (cameraManager.isOpen()) {
            return;
        }
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                cameraManager.openDriver(surfaceHolder);
            }
            if (handler == null) {
                handler = new CaptureActivityHandler(this, cameraManager);
            }
        } catch (Exception e) {
            handleException(getString(R.string.msg_camera_framework_bug));
        }
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    private void restartActivity() {
        onPause();
        // some device return wrong rotation state when rotate quickly.
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onResume();
    }

    private class MyOrientationDetector extends OrientationEventListener {

        private int lastOrientation = -1;

        MyOrientationDetector(Context context) {
            super(context);
        }

        void setLastOrientation(int rotation) {
            switch (rotation) {
                case Surface.ROTATION_90:
                    lastOrientation = 270;
                    break;
                case Surface.ROTATION_270:
                    lastOrientation = 90;
                    break;
                default:
                    lastOrientation = -1;
            }
        }

        @Override
        public void onOrientationChanged(int orientation) {
            if (orientation > 45 && orientation < 135) {
                orientation = 90;
            } else if (orientation > 225 && orientation < 315) {
                orientation = 270;
            } else {
                orientation = -1;
            }
            if ((orientation == 90 && lastOrientation == 270) || (orientation == 270 && lastOrientation == 90)) {
                restartActivity();
                lastOrientation = orientation;
            }
        }
    }

    /**
     * 中文乱码
     * <p>
     * 暂时解决大部分的中文乱码 但是还有部分的乱码无法解决 .
     * <p>
     * 如果您有好的解决方式 请联系 2221673069@qq.com
     * <p>
     * 我会很乐意向您请教 谢谢您
     */
    protected String recode(String str) {
        String formart = "";
        try {
            boolean ISO = Charset.forName("ISO-8859-1").newEncoder().canEncode(str);
            if (ISO) {
                formart = new String(str.getBytes("ISO-8859-1"), "GB2312");
            } else {
                formart = str;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return formart;
    }
}
