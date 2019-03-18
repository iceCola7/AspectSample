package com.cxz.aspect.module;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author admin
 * @date 2019/3/18
 * @desc 注解的处理方式
 */
@Aspect
public class AnnotationAspect {

    private static final String TAG = "cxz----->>";

    // 切入点： DebugTree 注解
    @Pointcut("execution(@com.cxz.aspect.sample.DebugTree * * (..))")
    public void DebugTraceMethod() {
    }

    @Before("DebugTraceMethod()")
    public void beforeDebugTraceMethod(JoinPoint joinPoint) {
        String key = joinPoint.getSignature().toString();
        Log.e(TAG, "注解方法之前执行: " + key);
    }

    @After("DebugTraceMethod()")
    public void afterDebugTraceMethod(JoinPoint joinPoint) {
        String key = joinPoint.getSignature().toString();
        Log.e(TAG, "注解方法之后执行: " + key);
    }

}
