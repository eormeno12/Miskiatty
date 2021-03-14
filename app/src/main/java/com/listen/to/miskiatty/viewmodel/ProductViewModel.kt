package com.listen.to.miskiatty.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.listen.to.miskiatty.model.Product
import com.listen.to.miskiatty.model.repository.products.ProductObservable

class ProductViewModel: ViewModel() {

    private val productObservable = ProductObservable()

    fun callProducts(){
        productObservable.callProductsROOM()
    }

    fun getProducts(): LiveData<List<Product>>{
        return productObservable.getProducts()
    }
}