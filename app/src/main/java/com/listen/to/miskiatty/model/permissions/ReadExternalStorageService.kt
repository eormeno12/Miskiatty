package com.listen.to.miskiatty.model.permissions

import android.app.Activity
import com.listen.to.miskiatty.model.permissions.PermissionsBase.Companion.REQUEST_KEY_READ_EXTERNAL_STORAGE

class ReadExternalStorageService(val activity: Activity) {

    private val permissionsBase = PermissionsBase(
            activity,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            REQUEST_KEY_READ_EXTERNAL_STORAGE
    )

    fun validatePermission(): Boolean = permissionsBase.validatePermission()

    fun askPermission() = permissionsBase.askPermission()
}