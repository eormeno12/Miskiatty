package com.listen.to.miskiatty.viewmodel

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.listen.to.miskiatty.model.database.Product
import com.listen.to.miskiatty.model.repository.products.ProductAddObservable

class ProductAddViewModel: ViewModel(){
    private val productAddObservable: ProductAddObservable = ProductAddObservable()

    fun addProductRoom(context: Context, lifecycle: Lifecycle, product: Product) =
        productAddObservable.insertProductRoom(context, lifecycle, product)

    fun updateProductRoom(context: Context, lifecycle: Lifecycle, product: Product) =
            productAddObservable.updateProductRoom(context, lifecycle, product)

    fun intentHasData(activity: AppCompatActivity) =
            productAddObservable.verifyIntentData(activity)

    fun callProduct(activity: AppCompatActivity) =
            productAddObservable.callProductExtra(activity)

    fun getProduct(): LiveData<Product> =
            productAddObservable.getProduct()
}