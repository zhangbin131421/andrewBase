package com.andrew.library.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class AndrewViewModel : ViewModel() {
    val loading = MutableLiveData<Boolean>()
    val refreshTrigger = MutableLiveData<Boolean>()
    fun attachLoading(otherState: MutableLiveData<Boolean>) {
        loading.observeForever {
            otherState.value = it
        }
    }

    /**
     * 加载数据
     */
    open fun refreshLoading() {
        loading.value = true
        refreshTrigger.value = true
    }

    /**
     * 下拉加载数据
     */
    open fun refreshPullDown() {
        refreshTrigger.value = true
    }
}