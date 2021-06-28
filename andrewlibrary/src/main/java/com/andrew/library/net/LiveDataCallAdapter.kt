package com.andrew.library.net


import androidx.lifecycle.LiveData
import com.andrew.library.base.AndrewApplication
import com.orhanobut.logger.Logger
import com.andrew.library.model.BaseResponse
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.atomic.AtomicBoolean


class LiveDataCallAdapter<T>(private val responseType: Type) : CallAdapter<T, LiveData<T>> {
    override fun adapt(call: Call<T>): LiveData<T> {

        return object : LiveData<T>() {
            private val started = AtomicBoolean(false)
            override fun onActive() {
                super.onActive()
                if (started.compareAndSet(false, true)) {//确保执行一次
                    call.enqueue(object : Callback<T> {
                        override fun onFailure(call: Call<T>, t: Throwable) {
                            val message = when (t) {
                                is SocketTimeoutException, is ConnectTimeoutException -> {
                                    "网络连接超时，请稍后再试。"
                                }
                                is ConnectException -> {
                                    "网络连接失败，请检查网络。"
                                }
                                is UnknownHostException -> {
                                    "网络连接失败，请检查网络。"
                                }
                                else -> {
                                    "网络连接失败"
                                }
                            }
                            Logger.e("onFailure======$t")
                            Logger.e("onFailure======$t.message")
                            val value = BaseResponse(0, null, message) as T
                            postValue(value)
                        }

                        override fun onResponse(call: Call<T>, response: Response<T>) {
//                            Logger.e("onResponse.code()======"+response.code())
//                            Logger.e("onResponse.Body()======"+response.body().toString())
//                            Logger.e("onResponse.errorBody()======"+response.errorBody().toString())
                            when (response.code()) {
                                200 -> {
                                    postValue(response.body())
                                }
                                500 -> {
                                    response.errorBody()?.let {
                                        val body = it.string()
                                        Logger.e("onResponse.errorBody()======$body")
                                        try {
                                            val obj = JSONObject(body)
//                                            val message = obj.getString("message")
                                            val code = obj.getInt("code")
                                            if (code == 1001) {
                                                AndrewApplication.instance.jumpLoginActivity()
                                                return
                                            }
                                        } catch (e: Exception) {
                                            Logger.e(e.message.toString())
                                        }
                                    }
                                    val value = BaseResponse(500, null, "服务器开小差了，请稍后再试") as T
                                    postValue(value)
                                }
                                else -> {
                                    val value = BaseResponse(0, null, "服务器异常，请稍后再试") as T
                                    postValue(value)
                                }
                            }

                        }
                    })
                }
            }
        }
    }

    override fun responseType() = responseType
}