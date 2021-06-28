package com.example.kotlin.base.viewmodel

import com.andrew.library.base.AndrewViewModel
import com.example.kotlin.mode.LayoutTitle
import com.example.kotlin.net.API

open class BaseViewModel : AndrewViewModel() {
    var layoutTitle: LayoutTitle? = null
    protected val api: API = API.Builder.getDefaultService()

}