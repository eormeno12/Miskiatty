package com.listen.to.miskiatty.model.permissions

import android.app.Activity
import android.content.pm.PackageManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class ExternalStorageService(val activity: Activity) {

    private val readExternalStoragePermission = android.Manifest.permission.READ_EXTERNAL_STORAGE
    private val REQUEST_EXTERNAL_STORAGE = 100

    fun validatePermission(): Boolean{

        return ActivityCompat.checkSelfPermission(
                activity.applicationContext,
                readExternalStoragePermission
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun askPermission(){
        val isExtraContextNeeded = ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                readExternalStoragePermission)

        if(isExtraContextNeeded){
            requestPermission()
        }else{
            requestPermission()
        }
    }

    private fun requestPermission(){
        activity.requestPermissions(arrayOf(readExternalStoragePermission), REQUEST_EXTERNAL_STORAGE)
    }
}