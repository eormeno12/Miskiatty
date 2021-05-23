package com.listen.to.miskiatty.model.repository.statistics

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import com.listen.to.miskiatty.model.database.Client
import com.listen.to.miskiatty.model.database.Order
import com.listen.to.miskiatty.model.database.Product

interface StatisticsRepository {
    fun callProductsByIdRoom(context: Context, lifecycle: Lifecycle, id: List<Int>)
    fun getProductsById(): MutableLiveData<List<Product>>
    fun callClientsRoom(context: Context, lifecycle: Lifecycle)
    fun getClients(): MutableLiveData<List<Client>>
    fun callOrdersRoom(context: Context, lifecycle: Lifecycle)
    fun getOrders(): MutableLiveData<List<Order>>
}