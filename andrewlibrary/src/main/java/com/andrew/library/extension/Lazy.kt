package com.andrew.library.extension

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

inline fun <reified T : ViewModel> FragmentActivity.androidViewModel() =
    lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(this.application)
        ).get(T::class.java)
    }

inline fun <reified T : ViewModel> FragmentActivity.viewModel() =
    lazy { ViewModelProvider(this).get(T::class.java) }

inline fun <reified T : ViewModel> FragmentActivity.viewModel(crossinline block: T.() -> Unit) =
    lazy {
        ViewModelProvider(this).get(T::class.java).apply(block)
    }

inline fun <reified T : ViewModel> Fragment.viewModel() =
    lazy { ViewModelProvider(this).get(T::class.java) }

//inline fun <reified T : ViewModel> Fragment.viewModelPFragment() =
//        lazy { this.parentFragment?.let { ViewModelProvider(it).get(T::class.java) } }

inline fun <reified T : ViewModel> Fragment.viewModelPFragment() =
    lazy { ViewModelProvider(parentFragment!!).get(T::class.java) }

inline fun <reified T : ViewModel> Fragment.viewModelActivity() =
    lazy { ViewModelProvider(this.requireActivity()).get(T::class.java) }

/**
 * 相同类型使用多个的情况下
 */
@JvmOverloads
inline fun <reified T : ViewModel> Fragment.viewModel(
    key: Int? = null,
    crossinline block: T.() -> Unit
) =
    lazy {
        ViewModelProvider(this).get(key.toString(), T::class.java)
            .apply(block)
    }


inline fun <reified T> Fragment.arg(key: String) =
    lazy {
        val ret = when (T::class.java) {
            String::class.java -> arguments?.getString(key)
            Int::class.java -> arguments?.getInt(key)
            else -> null
        }
        ret as T?
    }



