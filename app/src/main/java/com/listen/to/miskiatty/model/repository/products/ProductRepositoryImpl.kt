package com.listen.to.miskiatty.model.repository.products

import androidx.lifecycle.MutableLiveData
import com.listen.to.miskiatty.model.Product

class ProductRepositoryImpl: ProductRepository {

    private var products = MutableLiveData<List<Product>>()

    override fun callProductsROOM() {

    }

    override fun getProducts(): MutableLiveData<List<Product>> {
        return products
    }

}