package com.listen.to.miskiatty.model.permissions

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

class PermissionsBase(private val activity: Activity,
                      private val permission: String,
                      private val REQUEST_KEY: Int){

    companion object{
        const val REQUEST_KEY_READ_EXTERNAL_STORAGE = 100
        const val REQUEST_KEY_READ_CONTACTS = 101
    }

    fun validatePermission(): Boolean{

        return ActivityCompat.checkSelfPermission(
                activity.applicationContext,
                permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun askPermission(){
        val isExtraContextNeeded = ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                permission)

        if(isExtraContextNeeded){
            requestPermission()
        }else{
            requestPermission()
        }
    }

    private fun requestPermission(){
        activity.requestPermissions(arrayOf(permission), REQUEST_KEY)
    }
}