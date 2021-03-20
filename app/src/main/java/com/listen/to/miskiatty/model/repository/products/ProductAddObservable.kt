package com.listen.to.miskiatty.model.repository.products

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.listen.to.miskiatty.model.database.Product

class ProductAddObservable: BaseObservable(){
    private val productAddRepository = ProductAddRepositoryImpl()

    fun verifyIntentData(activity: AppCompatActivity) =
            productAddRepository.verifyIntentData(activity)

    fun callProductExtra(activity: AppCompatActivity) =
            productAddRepository.callProductExtra(activity)

    fun getProduct(): MutableLiveData<Product> =
            productAddRepository.getProduct()

    fun insertProductRoom(context: Context, lifecycle: Lifecycle, product: Product) =
            productAddRepository.insertProductRoom(context, lifecycle, product)

    fun updateProductRoom(context: Context, lifecycle: Lifecycle, product: Product) =
            productAddRepository.updateProductRoom(context, lifecycle, product)

}