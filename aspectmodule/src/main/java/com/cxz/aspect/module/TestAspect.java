package com.cxz.aspect.module;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * @author admin
 * @date 2019/3/18
 * @desc 演示常用通配符的使用
 */
@Aspect
public class TestAspect {

    private static final String TAG = "cxz----->>";

    /**
     * 切入点：testMethod 方法执行之前
     */
    @Before("call(* com.cxz.aspect.sample.MainActivity.testMethod(..))")
    public void onTestMethodBefore(JoinPoint joinPoint) {
        Log.e(TAG, "onTestMethodBefore: testMethod方法执行前执行...");
    }

    /**
     * 切入点：testMethod 方法
     */
    @Around("call(* com.cxz.aspect.sample.MainActivity.testMethod(..))")
    public void onTestMethodAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // @Around 会替换原先执行的代码，但如果你仍然希望执行原先的代码，可以使用joinPoint.proceed()。
        proceedingJoinPoint.proceed();
    }

    /**
     * 切入点：testMethod 方法执行之后
     */
    @After("call(* com.cxz.aspect.sample.MainActivity.testMethod(..))")
    public void onTestMethodAfter(JoinPoint joinPoint) {
        Log.e(TAG, "onTestMethodAfter: testMethod方法执行后执行...");
    }

    /**
     * 切入点：构造方法执行之前
     */
    @Before("execution(com.cxz.aspect.sample.Student.new(..))")
    public void beforeConstructor(JoinPoint joinPoint) {
        // 这是显示构造方法
        Log.e(TAG, "beforeConstructor: " + joinPoint.getThis().toString() + "," + joinPoint.getSignature().toString());
    }

    /**
     * 切入点：类 Student 的 age 属性的 get 方法
     */
    @Around("get(int com.cxz.aspect.sample.Student.age)")
    public int aroundFieldGet(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object object = proceedingJoinPoint.proceed();
        String age = object.toString();
        Log.e(TAG, "aroundFieldGet: " + age);
        return 50;
    }

    /**
     * 切入点：类 Student 的 age 属性的 set 方法
     */
    @Around("set(int com.cxz.aspect.sample.Student.age)")
    public void aroundFieldSet(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Log.e(TAG, "aroundFieldSet: " + proceedingJoinPoint.getThis().toString());
        proceedingJoinPoint.proceed();
    }

    /**
     * 切入点：构造方法执行之后
     */
    @After("execution(com.cxz.aspect.sample.Student.new(..))")
    public void afterConstructor(JoinPoint joinPoint) {
        Log.e(TAG, "afterConstructor: " + joinPoint.getThis().toString() + "," + joinPoint.getSignature().toString());
    }

    /**
     * 切入点：异常的 catch 块
     */
    @Before("handler(java.lang.Exception)")
    public void handlerMethod(JoinPoint joinPoint) {
        Log.e(TAG, "handlerMethod: " + joinPoint.toString());
    }

}
