package com.listen.to.miskiatty.model.repository.products

import androidx.lifecycle.MutableLiveData
import com.listen.to.miskiatty.model.Product

interface ProductRepository {
    fun getProducts(): MutableLiveData<List<Product>>
    fun callProductsROOM()
}