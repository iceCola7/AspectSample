package com.cxz.aspect.module;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * @author admin
 * @date 2019/3/22
 * @desc 防止连续点击
 */
@Aspect
public class ClickListenerAspect {

    private static final String TAG = "cxz----->>";

    private static Long lastClick = 0L;
    private static final Long FILTER_TIME = 1000L;

    @Around("execution(* android.view.View.OnClickListener.onClick(..))")
    public void clickFilter(ProceedingJoinPoint proceedingJoinPoint) {
        if (System.currentTimeMillis() - lastClick >= FILTER_TIME) {
            lastClick = System.currentTimeMillis();
            try {
                proceedingJoinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        } else {
            Log.e(TAG, "重复点击已过滤...");
        }

    }

}
