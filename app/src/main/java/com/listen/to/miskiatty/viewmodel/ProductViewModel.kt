package com.listen.to.miskiatty.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.listen.to.miskiatty.R
import com.listen.to.miskiatty.model.database.Product
import com.listen.to.miskiatty.model.adapters.AdapterCustomProducts
import com.listen.to.miskiatty.model.adapters.AdapterCustomListener
import com.listen.to.miskiatty.model.repository.products.ProductObservable

class ProductViewModel: ViewModel() {

    private val productObservable: ProductObservable = ProductObservable()
    var productsAdapter: ProductCustomAdapter? = null
    private var _productClicked = MutableLiveData<Product>()
    val productClicked: LiveData<Product> = _productClicked

    fun getRecyclerProductsAdapter(): ProductCustomAdapter?{
        return productsAdapter
    }

    fun setRecyclerProductsAdapter(){
        productsAdapter = ProductCustomAdapter(this, R.layout.template_product,
                object : ProductsListener{
                    override fun onClickListenerProduct(position: Int) {
                        _productClicked.value = getProductAt(position)
                    }
        })
    }

    fun callProducts(appContext: Context, lifecycle: Lifecycle){
        productObservable.callProductsROOM(appContext, lifecycle)
    }

    fun getProducts(): MutableLiveData<List<Product>> = productObservable.getProducts()

    fun setProductsInRecyclerAdapter(products: List<Product>){
        if(productsAdapter != null){
            productsAdapter?.setProductsList(products)
            productsAdapter?.notifyDataSetChanged()
        }
    }

    fun getProductAt(position: Int): Product? =
            productsAdapter?.getProductList()?.get(position)

    fun searchProduct(str: String){
        productsAdapter?.search(str)
    }
}