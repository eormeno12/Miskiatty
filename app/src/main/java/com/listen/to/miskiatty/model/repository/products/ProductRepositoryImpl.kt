package com.listen.to.miskiatty.model.repository.products

import androidx.lifecycle.MutableLiveData
import com.listen.to.miskiatty.R
import com.listen.to.miskiatty.model.Product

class ProductRepositoryImpl: ProductRepository {

    private var products = MutableLiveData<List<Product>>()

    override fun callProductsROOM() {
        products.value = listOf(Product(R.drawable.ic_logo_miskiatty, "KEKE", 20F, 15f, "1. DFSF"))
    }

    override fun getProducts(): MutableLiveData<List<Product>> {
        return products
    }

}