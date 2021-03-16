package com.listen.to.miskiatty.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.listen.to.miskiatty.R
import com.listen.to.miskiatty.model.Product
import com.listen.to.miskiatty.model.adapters.ProductCustomAdapter
import com.listen.to.miskiatty.model.repository.products.ProductObservable

class ProductViewModel: ViewModel() {

    private val productObservable: ProductObservable = ProductObservable()
    var productsAdapter: ProductCustomAdapter? = null

    fun getRecyclerProductsAdapter(): ProductCustomAdapter?{
        return productsAdapter
    }

    fun setRecyclerProductsAdapter(){
        productsAdapter = ProductCustomAdapter(
                this,
                R.layout.template_product)
    }

    fun callProducts(){
        productObservable.callProductsROOM()
    }

    fun getProducts(): MutableLiveData<List<Product>> = productObservable.getProducts()

    fun setProductsInRecyclerAdapter(products: List<Product>){
        if(productsAdapter != null){
            productsAdapter?.setProductsList(products)
            productsAdapter?.notifyDataSetChanged()
        }
    }

    fun getProductAt(position: Int): Product?{
        val products: List<Product>? = productObservable.getProducts().value
        return products?.get(position)
    }
}