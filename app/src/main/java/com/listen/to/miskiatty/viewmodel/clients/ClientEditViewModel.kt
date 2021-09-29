package com.listen.to.miskiatty.viewmodel.clients

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.listen.to.miskiatty.model.database.Client
import com.listen.to.miskiatty.model.permissions.ReadExternalStorageService
import com.listen.to.miskiatty.model.repository.clients.ClientEditRepository
import com.listen.to.miskiatty.model.repository.clients.ClientEditRepositoryImpl

class ClientEditViewModel: ViewModel() {
    val REQUEST_IMAGE_GALLERY = 100
    private val clientEditRepository: ClientEditRepository = ClientEditRepositoryImpl()

    var name = ""
    var phone = ""
    var address = ""

    fun updateClientRoom(context: Context, lifecycle: Lifecycle, client: Client) =
            clientEditRepository.updateClientRoom(context, lifecycle, client)

    fun callClient(activity: Activity, lifecycle: Lifecycle) =
            clientEditRepository.callClientExtra(activity, lifecycle)

    fun getClient(): LiveData<Client> =
            clientEditRepository.getClient()

    fun onClickPickImage(context: Context){
        val activity: Activity = context as Activity
        val externalStorageService = ReadExternalStorageService(activity)

        with(externalStorageService){
            if(validatePermission()){
                openGallery(activity)
            }else{
                askPermission()
            }
        }

    }

    private fun openGallery(activity: Activity){
        val gallery = Intent().apply {
            type = ("image/*")
            action = Intent.ACTION_GET_CONTENT
        }

        activity.startActivityForResult(gallery, REQUEST_IMAGE_GALLERY)
    }


}