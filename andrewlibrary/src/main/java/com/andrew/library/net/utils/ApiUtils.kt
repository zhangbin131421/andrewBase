package com.andrew.library.net.utils

import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody


fun objToRequestBody(obj: Any?): RequestBody {
    val gson = Gson()
    val objStr = gson.toJson(obj) //通过Gson将Bean转化为Json字符串形式
    return objStr.toRequestBody("application/json;charset=utf-8".toMediaType())
}

//fun generateRequestBody(requestDataMap: Map<String, String?>): Map<String, RequestBody>? {
//    val requestBodyMap: MutableMap<String, RequestBody> = HashMap()
//    for (key in requestDataMap.keys) {
//        var content = ""
//        if (requestDataMap[key] != null) {
//            content = requestDataMap[key].toString()
//        }
//        val requestBody: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), content)
//        requestBodyMap[key] = requestBody
//    }
//    return requestBodyMap
//}

