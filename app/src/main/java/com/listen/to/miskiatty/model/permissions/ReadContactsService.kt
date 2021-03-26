package com.listen.to.miskiatty.model.permissions

import android.app.Activity

class ReadContactsService(val activity: Activity) {

    private val permissionsBase = PermissionsBase(
            activity,
            android.Manifest.permission.READ_CONTACTS,
            PermissionsBase.REQUEST_KEY_READ_CONTACTS
    )

    fun validatePermission(): Boolean = permissionsBase.validatePermission()

    fun askPermission() = permissionsBase.askPermission()
}