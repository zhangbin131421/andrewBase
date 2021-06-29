package com.andrew.library.base

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.andrew.library.R
import com.andrew.library.observer.LoadingObserver
import com.andrew.library.utils.AppUtils
import com.andrew.library.utils.statusbar.StatusBarUtil


abstract class AndrewActivity : AppCompatActivity() {
    abstract val layoutId: Int
    var immersiveStatusBar = false
    var statusBarColor = R.color.theme_color
    var fontIconDark = false
    val loadingState = MutableLiveData<Boolean>()

    private var permissions: Array<out String>? = null
    private var requestCode: Int = -100
    private var description: String = ""
    private var activityRequestPermission = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (immersiveStatusBar){
            StatusBarUtil.setTranslucentStatus(this)
        }else{
            StatusBarUtil.setStatusBarColor(this, ContextCompat.getColor(this, statusBarColor))
            StatusBarUtil.setImmersiveStatusBar(this, fontIconDark)
        }
        setContentView(layoutId)
        loadingState.observe(this, LoadingObserver(this))
    }

    private fun checkDangerousPermissions(permissions: Array<out String>): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    permission
                )
            ) {
                return false
            }
        }
        return true
    }

    open fun handlePermissionResult(requestCode: Int, success: Boolean) {}

    open fun requestDangerousPermissions(
        permissions: Array<out String>, requestCode: Int,
        permissionsDescription: String
    ) {
        this.permissions = permissions
        this.requestCode = requestCode
        this.description = permissionsDescription
        if (checkDangerousPermissions(permissions)) {
            handlePermissionResult(requestCode, true)
            return
        }
//        ActivityCompat.requestPermissions(this, permissions, requestCode)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activityRequestPermission = true
            this.requestPermissions(permissions, requestCode)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (activityRequestPermission) {
            var granted = true
            var deniedNoHit = false//权限被禁用且不再提示
//        应用第一次安装，并且权限被禁用时，返回true
//        权限被禁用时，返回true
//        权限被禁用且不再提示时，返回false
//        已授权时返回false
            grantResults.forEachIndexed { index, value ->
                if (value != PackageManager.PERMISSION_GRANTED) {
                    granted = false
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            permissions[index]
                        )
                    ) {// 权限被禁用且不再提示时，返回false
                        deniedNoHit = true
                    }
                }
            }
            if (granted) {
                handlePermissionResult(requestCode, true)
            } else {
                if (deniedNoHit) { //勾选不再提醒/禁止后不再提示
                    val alertDialog = AlertDialog.Builder(this).setTitle("权限")
                        .setMessage("应用需要" + description + "权限，不授权将无法正常工作！")
                        .setNegativeButton("拒绝") { dialog, _ ->
                            dialog.dismiss()
                            handlePermissionResult(requestCode, false)
                        }
                        .setPositiveButton("去授权") { dialog, _ ->
                            dialog.dismiss()
                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            val uri = Uri.fromParts(
                                "package",
                                this.packageName,
                                null
                            ) //注意就是"package",不用改成自己的包名
                            intent.data = uri
                            startActivityForResult(intent, requestCode)
                        }.create()
                    alertDialog.setCancelable(false)
                    alertDialog.setCanceledOnTouchOutside(false)
                    alertDialog.show()
                } else {   //选择禁止
                    val alertDialog = AlertDialog.Builder(this).setTitle("权限申请")
                        .setMessage("应用需要" + description + "权限，不授权将无法正常工作！")
                        .setNegativeButton("拒绝") { dialog, _ ->
                            dialog.dismiss()
                            handlePermissionResult(requestCode, false)
                        }
                        .setPositiveButton("去允许") { dialog, _ ->
                            dialog.dismiss()
                            requestDangerousPermissions(permissions, requestCode, description)
                        }.create()
                    alertDialog.setCancelable(false)
                    alertDialog.setCanceledOnTouchOutside(false)
                    alertDialog.show()
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
        activityRequestPermission = false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == this.requestCode) {
            this.permissions?.let { requestDangerousPermissions(it, this.requestCode, description) }
            this.permissions = null
        }
    }

    override fun startActivityForResult(intent: Intent, requestCode: Int) {
        if (AppUtils.checkIntentActivity(intent)) {
            super.startActivityForResult(intent, requestCode)
        }
    }
}