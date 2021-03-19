package com.listen.to.miskiatty.viewmodel

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.listen.to.miskiatty.model.database.Product
import com.listen.to.miskiatty.model.repository.products.ProductDetailsObservable

class ProductDetailsViewModel: ViewModel() {

    private val productDetailsObservable: ProductDetailsObservable = ProductDetailsObservable()

    fun callProduct(activity: AppCompatActivity, lifecycle: Lifecycle) =
            productDetailsObservable.callProductSerializable(activity, lifecycle)


    fun getProduct(): LiveData<Product> =
            productDetailsObservable.getProductSerializable()

}