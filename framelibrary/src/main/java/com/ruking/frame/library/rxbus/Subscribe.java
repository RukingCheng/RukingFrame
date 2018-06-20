package com.ruking.frame.library.rxbus;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Ruking.Cheng
 * @descrilbe 注解类
 * @email 495095492@qq.com
 * @tel 18075121944
 * @date on 2017/8/23 8:54
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Subscribe {
    int code() default -1;

    ThreadMode threadMode() default ThreadMode.NONE;

    boolean sticky() default false;
}
