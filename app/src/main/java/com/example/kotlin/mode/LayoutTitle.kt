package com.example.kotlin.mode

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.andrew.library.listener.MyOnClickListener

class LayoutTitle {
    var backListener: MyOnClickListener<View>? = null
    var title: String = ""
    var tvRightText: String = ""
    val tvRightListener = MutableLiveData<MyOnClickListener<View>>()
    val imgRightListener = MutableLiveData<MyOnClickListener<View>>()
    val hideBottomLine = MutableLiveData(true)

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