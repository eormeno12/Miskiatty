package com.listen.to.miskiatty.model.repository.products

import androidx.databinding.BaseObservable
import androidx.lifecycle.MutableLiveData
import com.listen.to.miskiatty.model.Product

class ProductObservable: BaseObservable() {

    private val productRepository = ProductRepositoryImpl()
    fun callProductsROOM() {
        productRepository.callProductsROOM()
    }
    fun getProducts(): MutableLiveData<List<Product>>{
        return productRepository.getProducts()
    }
}