package com.listen.to.miskiatty.model.repository.orders

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import com.listen.to.miskiatty.model.database.Client
import com.listen.to.miskiatty.model.database.Order
import com.listen.to.miskiatty.model.database.Product

interface OrdersRepository {
    fun getOrders(): MutableLiveData<List<Order>>
    fun callOrdersROOM(context: Context, lifecycle: Lifecycle)
    fun callClientsRoom(context: Context, lifecycle: Lifecycle)
    fun getClient(id: Int): MutableLiveData<Client>
    fun callProductsRoom(context: Context, lifecycle: Lifecycle)
    fun getProducts(): MutableLiveData<List<Product>>
}