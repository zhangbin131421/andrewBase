package com.andrew.library.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.MutableLiveData
import com.andrew.library.observer.LoadingObserver

abstract class AndrewBaseFragmentDataBinding<BV : ViewDataBinding> : AndrewBaseFragment() {

    lateinit var bindingView: BV
    abstract val layoutId: Int
    private val loadingState = MutableLiveData<Boolean>()
    open val vm: AndrewViewModel? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        if (!this::bindingView.isInitialized) {
            bindingView = DataBindingUtil.inflate(inflater, layoutId, container, false)
            bindingView.lifecycleOwner = this
        }
        return bindingView.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadingState.observe(viewLifecycleOwner, LoadingObserver(requireActivity()))
        vm?.attachLoading(loadingState)

    }

    override fun onDestroy() {
        super.onDestroy()
        if (this::bindingView.isInitialized) {
            bindingView.unbind()
        }
    }

}