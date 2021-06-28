package com.andrew.library.listener

import android.view.View

/**
 * @author Maliang
 * @desc 防止多次点击抖动
 * @date 2017/12/13 下午4:45.
 */
abstract class OnViewClickListener : View.OnClickListener {
    private var lastClickTime = 0L
    override fun onClick(v: View) {
        val now = System.currentTimeMillis()
        if (now - lastClickTime >= 400L) {
            lastClickTime = now
            onViewClick(v)
        }
    }

    abstract fun onViewClick(v: View?)
}