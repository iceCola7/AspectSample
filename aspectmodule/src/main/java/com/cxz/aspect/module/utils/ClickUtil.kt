package com.cxz.aspect.module.utils

/**
 * @author chenxz
 * @date 2020/10/19
 * @desc
 */
object ClickUtil {

    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private const val MIN_CLICK_DELAY_TIME = 1000L
    private var lastClickTime: Long = 0

    /**
     * 防止重复点击
     */
    fun isFastClick(): Boolean {
        return isFastClick(MIN_CLICK_DELAY_TIME)
    }

    /**
     * 防止重复点击
     *
     * @param delayTime 时间间隔 单位毫秒
     */
    fun isFastClick(delayTime: Long): Boolean {
        val curClickTime = System.currentTimeMillis()
        val isFastClick: Boolean
        isFastClick = curClickTime - lastClickTime < delayTime
        lastClickTime = curClickTime
        return isFastClick
    }

}