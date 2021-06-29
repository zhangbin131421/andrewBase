package com.andrew.library.base

import android.app.Application
import androidx.multidex.MultiDex
import com.tencent.mmkv.MMKV


open class AndrewApplication : Application() {
    companion object {
        lateinit var instance: AndrewApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        MultiDex.install(this)
        MMKV.initialize(this)// /data/user/0/com.szybkj.task/files/mmkv

    }

    open fun jumpLoginActivity() {}
}