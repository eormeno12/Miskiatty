package com.listen.to.miskiatty.model.repository.products

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import com.listen.to.miskiatty.model.database.Product

interface ProductDetailsRepository {
    fun callProductRoom(activity: AppCompatActivity, lifecycle: Lifecycle)
    fun getProduct(): MutableLiveData<Product>
}