package com.listen.to.miskiatty.model.repository.products

import android.content.Context
import androidx.databinding.BaseObservable
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import com.listen.to.miskiatty.model.database.Product

class ProductObservable: BaseObservable() {

    private val productRepository = ProductRepositoryImpl()
    fun callProductsROOM(appContext: Context, lifecycle: Lifecycle) {
        productRepository.callProductsROOM(appContext, lifecycle)
    }

    fun getProducts(): MutableLiveData<List<Product>>{
        return productRepository.getProducts()
    }

    fun deleteProductROOM(context: Context, lifecycle: Lifecycle, product: Product) =
        productRepository.deleteProductROOM(context, lifecycle, product)
}