package com.ruking.frame.library.rxbus;

/**
 * @author Ruking.Cheng
 * @descrilbe 线程处理类
 * @email 495095492@qq.com
 * @tel 18075121944
 * @date on 2017/8/23 8:54
 */
public enum ThreadMode {

    /**
     * computation thread
     */
    COMPUTATION,

    NONE,

    /**
     * android main thread
     */
    MAIN,


    /**
     * new thread
     */
    NEW_THREAD,

    /**
     * io
     */
    IO

}
