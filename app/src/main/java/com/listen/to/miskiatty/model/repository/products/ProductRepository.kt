package com.listen.to.miskiatty.model.repository.products

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import com.listen.to.miskiatty.model.database.Product

interface ProductRepository {
    fun getProducts(): MutableLiveData<List<Product>>
    fun callProductsROOM(context: Context, lifecycle: Lifecycle)
    fun deleteProductROOM(context: Context, lifecycle: Lifecycle, product: Product)
}