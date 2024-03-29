package com.listen.to.miskiatty.viewmodel.products

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.listen.to.miskiatty.model.database.Product
import com.listen.to.miskiatty.model.permissions.ReadExternalStorageService
import com.listen.to.miskiatty.model.repository.products.ProductAddObservable

class ProductAddViewModel: ViewModel(){
    val REQUEST_IMAGE_GALLERY = 100
    
    private val productAddObservable: ProductAddObservable = ProductAddObservable()

    fun addProductRoom(context: Context, lifecycle: Lifecycle, product: Product) =
        productAddObservable.insertProductRoom(context, lifecycle, product)

    fun updateProductRoom(context: Context, lifecycle: Lifecycle, product: Product) =
            productAddObservable.updateProductRoom(context, lifecycle, product)

    fun intentHasData(activity: AppCompatActivity) =
            productAddObservable.verifyIntentData(activity)

    fun callProduct(activity: AppCompatActivity, lifecycle: Lifecycle) =
            productAddObservable.callProductExtra(activity, lifecycle)

    fun getProduct(): LiveData<Product> =
            productAddObservable.getProduct()

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