package com.listen.to.miskiatty.model.repository.products

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import com.listen.to.miskiatty.model.database.Product

interface ProductAddRepository {
    fun verifyIntentData(activity: AppCompatActivity): Boolean
    fun callProductExtra(activity: AppCompatActivity)
    fun getProduct(): MutableLiveData<Product>
    fun insertProductRoom(context: Context, lifecycle: Lifecycle, product: Product)
    fun updateProductRoom(context: Context, lifecycle: Lifecycle, product: Product)
}