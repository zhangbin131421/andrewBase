package com.andrew.library.net.utils

import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

/**
 * @author: andrew zhang
 * @date: 2021/3/26
 */
class MTreeMap : TreeMap<String, Any>() {

    override fun toString(): String {
//        sortKey()
        val params = StringBuilder()
        params.append('{')
        this.forEach {
            if (params.length > 1) {
                params.append(',')
            }
//            params.append("\"${it.key}\":\"${it.value}\"")
            if (it.value is String) {
                params.append("\"${it.key}\":\"${it.value}\"")
            } else {
                params.append("\"${it.key}\":").append(Gson().toJson(it.value))
            }
        }
        params.append('}')
        return params.toString()
    }

    private fun sortKey() {
        val params = StringBuilder()
        this.forEach {
//            params.append("${it.key}=${it.value}&")
            if (it.value is String) {
                params.append("${it.key}=${it.value}&")
            }
        }
        params.append("secret=yiyongok188yibaook")
        val secret = md5Sign(params.toString())
        this["secret"] = secret
    }

    private fun md5Sign(input: String): String {
        var md5After = ""
        try {
            val md = MessageDigest.getInstance("MD5")
            md.update(input.toByteArray())
            val b = md.digest()
            var i: Int
            val buf = StringBuffer("")
            for (offset in b.indices) {
                i = b[offset].toInt()
                if (i < 0) i += 256
                if (i < 16) buf.append("0")
                buf.append(Integer.toHexString(i))
            }
            md5After = buf.toString()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return md5After
    }

    fun toRequestBody(): RequestBody {
        return toString().toRequestBody("application/json;charset=utf-8".toMediaType())
    }
}