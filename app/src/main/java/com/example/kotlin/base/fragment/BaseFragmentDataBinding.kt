package com.example.kotlin.base.fragment

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.andrew.library.base.AndrewBaseFragmentDataBinding

abstract class BaseFragmentDataBinding<BV : ViewDataBinding> : AndrewBaseFragmentDataBinding<BV>() {


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}