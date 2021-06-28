package com.andrew.library.model

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.andrew.library.listener.MyOnClickListener

open class AndrewLayoutTitle {
    var backListener: MyOnClickListener<View>? = null
    var title: String = ""
    var tvRightText: String = ""
    var tvRightListener = MutableLiveData<MyOnClickListener<View>>()
    var imgRightListener = MutableLiveData<MyOnClickListener<View>>()

    fun clickBack(view: View) {
        backListener?.onClick(view)
    }

    fun clickTvRight(view: View) {
        tvRightListener.value?.onClick(view)
    }


    fun clickImgRight(view: View) {
        imgRightListener.value?.onClick(view)
    }


}