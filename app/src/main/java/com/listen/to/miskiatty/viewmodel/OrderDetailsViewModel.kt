package com.listen.to.miskiatty.viewmodel

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.listen.to.miskiatty.R
import com.listen.to.miskiatty.model.adapters.AdapterCustomOrderProducts
import com.listen.to.miskiatty.model.database.Client
import com.listen.to.miskiatty.model.database.Order
import com.listen.to.miskiatty.model.database.Product
import com.listen.to.miskiatty.model.repository.orders.OrderDetailsRepositoryImpl

class OrderDetailsViewModel: ViewModel() {

    private val orderDetailsRepository: OrderDetailsRepositoryImpl = OrderDetailsRepositoryImpl()
    var orderOrderProductsAdapter: AdapterCustomOrderProducts? = null

    fun getRecyclerOrdersProductsAdapter(): AdapterCustomOrderProducts?{
        return orderOrderProductsAdapter
    }

    fun setRecyclerOrdersAdapter(){
        orderOrderProductsAdapter = AdapterCustomOrderProducts(this, R.layout.template_order_products)
    }

    fun setProductsInRecyclerAdapter(products: List<Product>) {
        if (orderOrderProductsAdapter != null) {
            orderOrderProductsAdapter?.setProductsList(products)
            orderOrderProductsAdapter?.notifyDataSetChanged()
        }
    }

    fun callProducts(appContext: Context, lifecycle: Lifecycle) =
            orderDetailsRepository.callProductsRoom(appContext, lifecycle)


    fun getProducts(): MutableLiveData<List<Product>> = orderDetailsRepository.getProducts()

    fun getProductAt(position: Int): Product? =
            orderOrderProductsAdapter?.getProductsList()?.get(position)

    fun callOrder(activity: AppCompatActivity, lifecycle: Lifecycle) =
            orderDetailsRepository.callOrderRoom(activity, lifecycle)

    fun getOrder(): LiveData<Order> =
            orderDetailsRepository.getOrder()

    fun callClientById(context: Context, lifecycle: Lifecycle, id: Int) =
            orderDetailsRepository.callClientByIdRoom(context, lifecycle, id)

    fun getClientById(): LiveData<Client> =
            orderDetailsRepository.getClient()
}
