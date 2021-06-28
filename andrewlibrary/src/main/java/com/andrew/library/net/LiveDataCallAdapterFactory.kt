package com.andrew.library.net

import androidx.lifecycle.LiveData
import retrofit2.CallAdapter
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class LiveDataCallAdapterFactory : CallAdapter.Factory() {
    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {
//        val responseType: Type
//        Logger.e("getRawType(returnType)=" + getRawType(returnType).name)
//        用于获取泛型的原始类型 如 Call<Requestbody> 中的 Call
        if (getRawType(returnType) != LiveData::class.java) {//不支持不是LiveData类型的
            throw IllegalStateException("return type must be parameterized")
        }
        // 用于获取泛型的参数 如 Call<Requestbody> 中 Requestbody
        val observableType = getParameterUpperBound(0, returnType as ParameterizedType)
        val rawObservableType = getRawType(observableType)
        val responseType = if (rawObservableType == Response::class.java) {
            if (observableType !is ParameterizedType) {
                throw IllegalArgumentException("Response must be parameterized")
            }
            getParameterUpperBound(0, observableType)
        } else {
            observableType
        }
        return LiveDataCallAdapter<Any>(responseType)
    }
}