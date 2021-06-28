package com.andrew.library.base

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import java.lang.ref.WeakReference

interface IDangerousPermission {
    var contextRef: WeakReference<Activity>
    var fragmentRef: WeakReference<Fragment>?
    var functionCode: Int
    fun handlePermissionGrant(requestCode: Int)
    fun handlePermissionGrant(requestCode: Int, _functionCode: Int)


    private fun checkDangerousPermissions(permissions: Array<String>): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }
        contextRef.get()?.let { activity ->
            for (permission in permissions) {
                if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                    return false
                }
            }
        }
        return true
    }

    fun requestDangerousPermissions(permissions: Array<String>, requestCode: Int) {
        if (checkDangerousPermissions(permissions)) {
            if (functionCode == 0) {
                handlePermissionGrant(requestCode)
            } else {
                handlePermissionGrant(requestCode, functionCode)
            }
            return
        }
        contextRef.get()?.let { ActivityCompat.requestPermissions(it, permissions, requestCode) }
    }

    fun requestDangerousPermissions(permissions: Array<String>, requestCode: Int, _functionCode: Int) {
        functionCode = _functionCode
        requestDangerousPermissions(permissions, requestCode)
    }
}