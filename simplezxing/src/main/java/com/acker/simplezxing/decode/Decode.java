package com.acker.simplezxing.decode;

import android.os.Handler;

import com.acker.simplezxing.camera.CameraManager;
import com.acker.simplezxing.view.ViewfinderView;
import com.google.zxing.Result;

/**
 * @author Ruking.Cheng
 * @descrilbe TODO
 * @email 495095492@qq.com
 * @tel 18075121944
 * @date on 2018/4/18 下午2:04
 */
public interface Decode {
    CameraManager getCameraManager();

    Handler getHandler();

    void handleDecode(Result obj);

    ViewfinderView getViewfinderView();

    void drawViewfinder();
}
