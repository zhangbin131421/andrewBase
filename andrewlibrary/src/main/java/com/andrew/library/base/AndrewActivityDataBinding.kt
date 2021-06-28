package com.andrew.library.base

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class AndrewActivityDataBinding<BV : ViewDataBinding> : AndrewActivity() {
    lateinit var bindingView: BV
    open val vm: AndrewViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingView = DataBindingUtil.setContentView(this, layoutId)
        bindingView.lifecycleOwner = this
        vm?.attachLoading(loadingState)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (this::bindingView.isInitialized) {
            bindingView.unbind()
        }
    }
}