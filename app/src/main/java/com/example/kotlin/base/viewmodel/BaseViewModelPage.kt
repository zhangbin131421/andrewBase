package com.example.kotlin.base.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.andrew.library.model.BaseResponse
import com.andrew.library.utils.ConstantsUtil
import com.andrew.library.utils.ToastUtils
import com.example.kotlin.mode.Page

open class BaseViewModelPage : BaseViewModel() {
    val hasData = MutableLiveData(true)
    protected val page = MutableLiveData<Int>()
    val refreshing = MutableLiveData(false)
    val moreLoading = MutableLiveData(false)
    val hasMore = MutableLiveData(false)
    val autoRefresh = MutableLiveData<Boolean>()//SmartRefreshLayout自动刷新标记

    fun loadMore() {
        page.value = (page.value ?: 0) + 1
        moreLoading.value = true
    }

    /**
     * 自动刷新
     */
    fun autoRefresh() {
        autoRefresh.value = true
    }

    open fun refreshPage() {
        page.value = 1
        refreshing.value = true
    }

    /**
     * 处理分页数据
     */
    fun <T> mapPage(source: LiveData<BaseResponse<Page<T>>>): LiveData<Page<T>> {
        return Transformations.map(source) {
            loading.value = false
            refreshing.value = false
            moreLoading.value = false
            if (it.success()) {
                hasMore.value = it?.data?.page!! < it.data?.totalPage!!
            } else {
                hasMore.value = false
                ToastUtils.show(it.msg)
            }

            it.data ?: Page(1, 20, ArrayList(), 0, 0)
        }
    }

    val mapQueryParam = hashMapOf<String, String>()

    init {
        mapQueryParam["pageSize"] = ConstantsUtil.PAGE_SIZE.toString()
    }


}