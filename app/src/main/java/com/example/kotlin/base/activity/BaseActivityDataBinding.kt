package com.example.kotlin.base.activity

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.andrew.library.base.AndrewActivityDataBinding
import com.andrew.library.listener.MyOnClickListener
import com.example.kotlin.base.viewmodel.BaseViewModel

abstract class BaseActivityDataBinding<BV : ViewDataBinding> : AndrewActivityDataBinding<BV>() {
    override val vm: BaseViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm?.layoutTitle?.backListener = object : MyOnClickListener<View> {
            override fun onClick(t: View) {
                onBackPressed()
            }
        }
    }

//    override fun getResources(): Resources {
//        val res = super.getResources()
//        val config = res.configuration
//        config.fontScale = SpUtil.getInstance().fontSize //1 设置正常字体大小的倍数
//        res.updateConfiguration(config, res.displayMetrics)
//        return res
//    }
}