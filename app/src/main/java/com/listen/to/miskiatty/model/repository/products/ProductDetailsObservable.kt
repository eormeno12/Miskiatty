package com.listen.to.miskiatty.model.repository.products

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BaseObservable
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import com.listen.to.miskiatty.model.database.Product

class ProductDetailsObservable: BaseObservable() {
    private val productDetailsRepository = ProductDetailsRepositoryImpl()

    fun callProductSerializable(activity: AppCompatActivity, lifecycle: Lifecycle) =
            productDetailsRepository.callProduct(activity, lifecycle)

    fun getProductSerializable(): MutableLiveData<Product> =
            productDetailsRepository.getProduct()

}