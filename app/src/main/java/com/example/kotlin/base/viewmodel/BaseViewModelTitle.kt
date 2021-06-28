package com.example.kotlin.base.viewmodel

import com.example.kotlin.mode.LayoutTitle


open class BaseViewModelTitle : BaseViewModel() {
    init {
        layoutTitle = LayoutTitle()
    }
}