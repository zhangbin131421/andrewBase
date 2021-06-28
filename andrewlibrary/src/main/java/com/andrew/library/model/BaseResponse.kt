package com.andrew.library.model

data class BaseResponse<T>(
    val code: Int,
    var data: T?,
    val msg: String
) {
    fun success(): Boolean {
        return code == 200
    }
}