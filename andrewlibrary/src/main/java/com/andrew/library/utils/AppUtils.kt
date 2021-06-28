package com.andrew.library.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.provider.Settings
import com.andrew.library.base.AndrewApplication
import com.orhanobut.logger.Logger
import java.net.Inet4Address
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException
import java.util.*


object AppUtils {

    fun getAuthority(): String {
        return "${AndrewApplication.instance.packageName}.fileprovider"
    }

    /**
     * 获取版本名称
     *
     * @param context 上下文
     * @return 版本名称
     */
    fun getVersionName(context: Context): String {
        //获取包信息
        try {
            val packageInfo = context.applicationContext.packageManager.getPackageInfo(
                context.applicationContext.packageName,
                0
            )
            //返回版本号
            return packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return ""
    }

    /**
     * 获取版本号
     *
     * @param context 上下文
     * @return 版本号
     */
    fun getVersionCode(context: Context): Int {

        //获取包管理器
        val pm = context.packageManager
        //获取包信息
        try {
            val packageInfo = pm.getPackageInfo(context.packageName, 0)
            //返回版本号
            return packageInfo.versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return 0
    }

    /**
     * 获取App的名称
     *
     * @param context 上下文
     * @return 名称
     */
    fun getAppName(context: Context): String? {
        val pm = context.packageManager
        //获取包信息
        try {
            val packageInfo = pm.getPackageInfo(context.packageName, 0)
            //获取应用 信息
            val applicationInfo = packageInfo.applicationInfo
            //获取albelRes
            val labelRes = applicationInfo.labelRes
            //返回App的名称
            return context.resources.getString(labelRes)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return null
    }

    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private const val MIN_CLICK_DELAY_TIME = 1000
    private var lastClickTime: Long = 0
    fun isNormalClick(): Boolean {
        val curClickTime = System.currentTimeMillis()
        val flag = (curClickTime - lastClickTime) > MIN_CLICK_DELAY_TIME
        lastClickTime = curClickTime
        return flag
    }

    private var mActivityJumpTag = ""

    /**
     * 检查是否重复跳转，不需要则重写方法并返回true
     */
    fun checkIntentActivity(intent: Intent): Boolean {

        // 默认检查通过
        var result = true
        // 标记对象
        val tag = when {
            intent.component != null -> { // 显式跳转
                intent.component!!.className
            }
            intent.action != null -> { // 隐式跳转
                intent.action!!
            }
            else -> {
                return false
            }
        }
        if (!isNormalClick() && tag == mActivityJumpTag) { // 检查不通过
            result = false
        }
        // 记录启动标记和时间
        mActivityJumpTag = tag
        return result
    }

    /**
     * 系统是否允许自动旋转屏幕
     *
     * @return
     */
    fun isAutoRotateOn(context: Context): Boolean {
        //获取系统是否允许自动旋转屏幕
        return Settings.System.getInt(
            context.contentResolver,
            Settings.System.ACCELEROMETER_ROTATION,
            0
        ) == 1
    }

    /**
     * 获取当前ip4地址
     */
    fun getLocalIpAddress(): String {
        try {
            val en: Enumeration<NetworkInterface> = NetworkInterface.getNetworkInterfaces()
            while (en.hasMoreElements()) {
                val networkInterface: NetworkInterface = en.nextElement()
                val enumIpAddress: Enumeration<InetAddress> = networkInterface.inetAddresses
                while (enumIpAddress.hasMoreElements()) {
                    val inetAddress: InetAddress = enumIpAddress.nextElement()
                    if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                        return inetAddress.hostAddress
                    }
                }
            }
        } catch (ex: SocketException) {
            Logger.e(ex.toString())
        }
        return ""
    }

    /**
     * 判断是否有网络连接
     * @param context
     * @return
     */
    fun isNetworkConnected(context: Context): Boolean {
        val mConnectivityManager =
            context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val mNetworkInfo = mConnectivityManager.activeNetworkInfo
        if (mNetworkInfo != null) {
            return mNetworkInfo.isAvailable
        }

        return false
    }
}