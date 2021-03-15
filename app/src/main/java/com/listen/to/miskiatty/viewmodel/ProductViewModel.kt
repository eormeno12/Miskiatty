package com.listen.to.miskiatty.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.listen.to.miskiatty.R
import com.listen.to.miskiatty.model.Product
import com.listen.to.miskiatty.model.adapters.ProductCustomAdapter
import com.listen.to.miskiatty.model.repository.products.ProductObservable

class ProductViewModel: ViewModel() {

    private val productObservable = ProductObservable()
    private var productsAdapter: ProductCustomAdapter? = null

    fun callProducts(){
        productObservable.callProductsROOM()
    }

    fun getProducts(): LiveData<List<Product>> = productObservable.getProducts()

    fun setProductsInRecyclerAdapter(products: List<Product>){
        productsAdapter?.setProductsList(products)
        productsAdapter?.notifyDataSetChanged()
    }

    fun getRecyclerProductsAdapter(): ProductCustomAdapter?{
        productsAdapter = ProductCustomAdapter(this, R.layout.template_product)
        return productsAdapter
    }

    fun getProductAt(position: Int): Product?{
        val products: List<Product>? = productObservable.getProducts().value
        return products?.get(position)
    }
}