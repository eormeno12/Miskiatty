package com.listen.to.miskiatty.viewmodel

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.listen.to.miskiatty.R
import com.listen.to.miskiatty.model.adapters.AdapterCustomOrdersClient
import com.listen.to.miskiatty.model.database.Client
import com.listen.to.miskiatty.model.database.Order
import com.listen.to.miskiatty.model.repository.clients.ClientDetailsRepository
import com.listen.to.miskiatty.model.repository.clients.ClientDetailsRepositoryImpl

class ClientDetailsViewModel: ViewModel() {
    private val clientDetailsRepository: ClientDetailsRepository = ClientDetailsRepositoryImpl()

    private var ordersAdapter: AdapterCustomOrdersClient? = null

    fun getRecyclerClientsAdapter(): AdapterCustomOrdersClient? = ordersAdapter

    fun setRecyclerOrdersAdapter(){
        ordersAdapter = AdapterCustomOrdersClient(
                this, R.layout.template_order_client)
    }

    fun getOrderAt(position: Int): Order? =
            ordersAdapter?.getOrdersList()?.get(position)

    fun getProductList(position: Int): String{
        val allProducts = getProducts().value
        val order = getOrderAt(position)
        var productsNameList = ""

        if(order != null && allProducts != null)
            for (id in order.products)
                for(idx in 0 until allProducts.count())
                    if(allProducts[idx].id == id){
                        if(idx < 5){
                            productsNameList += "- ${order.productsQuantity[idx]} ${allProducts[idx].name}"
                            if(idx != order.products.count() - 1)
                                productsNameList += "\n"
                        }
                        else
                            break

                        if(idx == 4)
                            productsNameList += "\nY más..."
                    }

        return productsNameList
    }

    fun setOrdersInRecyclerAdapter(orders: List<Order>) =
            ordersAdapter?.setOrdersList(orders)

    fun callClient(activity: AppCompatActivity, lifecycle: Lifecycle) =
            clientDetailsRepository.callClientRoom(activity, lifecycle)


    fun getClient(): LiveData<Client> =
            clientDetailsRepository.getClient()

    fun callOrdersById(activity: AppCompatActivity, lifecycle: Lifecycle, id: List<Int>) =
            clientDetailsRepository.callOrdersById(activity, lifecycle, id)

    fun getOrdersById() = clientDetailsRepository.getOrdersById()

    fun callProducts(context: Context, lifecycle: Lifecycle) =
            clientDetailsRepository.callProducts(context, lifecycle)

    fun getProducts() = clientDetailsRepository.getProducts()

}