package com.cxz.aspect.module;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * @author chenxz
 * @date 2019/3/18
 * @desc
 */
@Aspect
public class TreeAspect {

    public static final String TAG = "cxz----->>";

    @Before("execution(* android.app.Activity.on*(..))")
    public void onActivityMethodBefore(JoinPoint joinPoint) {
        String key = joinPoint.getSignature().toString();
        Log.e(TAG, "onActivityMethodBefore: 切面的方法执行了--" + key);
    }

    @Around("execution(* com.cxz.aspect.sample.MainActivity.method2(..))")
    public void onMethodAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String key = proceedingJoinPoint.getSignature().toString();

        // 这句代码的含义就是执行了这些方法
        // @Around 会替换原先执行的代码，但如果你仍然希望执行原先的代码，可以使用joinPoint.proceed()。
        proceedingJoinPoint.proceed();

        long endTime = System.currentTimeMillis();
        Log.e(TAG, "方法用时：" + (endTime - startTime));
    }

    @After("execution(* com.cxz.aspect.sample.MainActivity.method2(..))")
    public void onMethodAfter(JoinPoint joinPoint) {
        String key = joinPoint.getSignature().toString();
        Log.e(TAG, "方法结束时执行..." + key);
    }

}
