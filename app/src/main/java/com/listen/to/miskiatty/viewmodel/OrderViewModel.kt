package com.listen.to.miskiatty.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.listen.to.miskiatty.R
import com.listen.to.miskiatty.model.adapters.AdapterCustomListener
import com.listen.to.miskiatty.model.adapters.AdapterCustomOrders
import com.listen.to.miskiatty.model.database.Client
import com.listen.to.miskiatty.model.database.Order
import com.listen.to.miskiatty.model.database.Product
import com.listen.to.miskiatty.model.repository.orders.OrdersRepository
import com.listen.to.miskiatty.model.repository.orders.OrdersRepositoryImpl
import com.listen.to.miskiatty.view.ui.orders.OrderAddActivity

class OrderViewModel: ViewModel() {
    private val ordersRepository: OrdersRepository = OrdersRepositoryImpl()
    var ordersAdapter: AdapterCustomOrders? = null
    private var _orderClicked = MutableLiveData<Order>()
    val orderClicked: LiveData<Order> = _orderClicked

    fun getRecyclerOrdersAdapter(): AdapterCustomOrders?{
        return ordersAdapter
    }

    fun setRecyclerOrdersAdapter(){
        ordersAdapter = AdapterCustomOrders(this, R.layout.template_order,
                object : AdapterCustomListener {
                    override fun onClickListener(position: Int) {
                        _orderClicked.value = getOrderAt(position)
                    }
                })
    }

    fun callOrders(appContext: Context, lifecycle: Lifecycle) {
        ordersRepository.callOrdersROOM(appContext, lifecycle)
    }

    fun getOrders(): MutableLiveData<List<Order>> = ordersRepository.getOrders()

    fun setOrdersInRecyclerAdapter(orders: List<Order>) {
        if (ordersAdapter != null) {
            ordersAdapter?.setOrdersList(orders)
            ordersAdapter?.notifyDataSetChanged()
        }
    }

    fun getOrderAt(position: Int): Order? =
            ordersAdapter?.getOrdersList()?.get(position)

    fun getDate(position: Int): String{
        val text = getOrderAt(position)?.deliveryDate.toString().split(" ")
        val chunks = text.chunked(2)
        return chunks[0][0]
    }

    fun callProducts(appContext: Context, lifecycle: Lifecycle) =
        ordersRepository.callProductsRoom(appContext, lifecycle)

    fun getProducts(): MutableLiveData<List<Product>> = ordersRepository.getProducts()

    fun getProductList(position: Int): String{
        val allProducts = getProducts().value
        val order = getOrderAt(position)
        var productsNameList = ""

        if(order != null && allProducts != null)
            for (id in order.products)
                for(idx in 0 until allProducts.count())
                    if(allProducts[idx].id == id)
                        productsNameList += "- ${order.productsQuantity[idx]} ${allProducts[idx].name}\n"

        return productsNameList
    }

    fun searchOrder(str: String) {
        ordersAdapter?.search(str)
    }

    fun onClickAddOrder(context: Context) =
            context.startActivity(Intent(context.applicationContext, OrderAddActivity::class.java))

    fun callClients(context: Context, lifecycle: Lifecycle) =
            ordersRepository.callClientsRoom(context, lifecycle)

    fun getClientById(id: Int): LiveData<Client> =
            ordersRepository.getClient(id)

}