package com.listen.to.miskiatty.viewmodel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.listen.to.miskiatty.R
import com.listen.to.miskiatty.model.adapters.AdapterCustomClients
import com.listen.to.miskiatty.model.database.Product
import com.listen.to.miskiatty.model.adapters.AdapterCustomProducts
import com.listen.to.miskiatty.model.adapters.AdapterCustomListener
import com.listen.to.miskiatty.model.repository.products.ProductObservable
import com.listen.to.miskiatty.view.ui.products.ProductAddActivity

class ProductViewModel: ViewModel() {

    private val productObservable: ProductObservable = ProductObservable()
    var productsAdapter: AdapterCustomProducts? = null
    private var _productClicked = MutableLiveData<Product>()
    val productClicked: LiveData<Product> = _productClicked

    fun getRecyclerProductsAdapter(): AdapterCustomProducts?{
        return productsAdapter
    }

    fun setRecyclerProductsAdapter(){
        productsAdapter = AdapterCustomProducts(this, R.layout.template_product,
                object : AdapterCustomListener{
                    override fun onClickListener(position: Int) {
                        _productClicked.value = getProductAt(position)
                    }
                }
        )
    }

    fun callProducts(appContext: Context, lifecycle: Lifecycle) {
        productObservable.callProductsROOM(appContext, lifecycle)
    }

    fun getProducts(): MutableLiveData<List<Product>> = productObservable.getProducts()

    fun deleteProductROOM(context: Context, lifecycle: Lifecycle, product: Product) =
        productObservable.deleteProductROOM(context, lifecycle, product)

    fun setProductsInRecyclerAdapter(products: List<Product>) {
        if (productsAdapter != null) {
            productsAdapter?.setProductsList(products)
            productsAdapter?.notifyDataSetChanged()
        }
    }

    fun getProductAt(position: Int): Product? =
            productsAdapter?.getProductList()?.get(position)

    fun searchProduct(str: String) {
        productsAdapter?.search(str)
    }

    fun setUpProductDeleteSwiping(rv: RecyclerView){
        productsAdapter?.setUpProductDeleteSwiping(rv)
    }

    fun onClickAddProduct(context: Context) =
            context.startActivity(Intent(context.applicationContext, ProductAddActivity::class.java))

}
