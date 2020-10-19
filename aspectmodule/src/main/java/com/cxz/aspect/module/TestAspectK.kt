package com.cxz.aspect.module

import android.util.Log
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before

/**
 * @author chenxz
 * @date 2020/10/19
 * @desc
 */
@Aspect
class TestAspectK {

    private val TAG = "cxz---k-->>"

    /**
     * 切入点：testMethod 方法执行之前
     */
    @Before("call(* com.cxz.aspect.sample.MainActivity.testMethod(..))")
    fun onTestMethodBefore(joinPoint: JoinPoint?) {
        Log.e(TAG, "onTestMethodBefore: testMethod方法执行前执行...")
    }

}