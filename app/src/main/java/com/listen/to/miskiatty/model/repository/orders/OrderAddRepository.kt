package com.listen.to.miskiatty.model.repository.orders

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import com.listen.to.miskiatty.model.database.Client
import com.listen.to.miskiatty.model.database.Order
import com.listen.to.miskiatty.model.database.Product

interface OrderAddRepository {
    fun verifyIntentData(activity: AppCompatActivity): Boolean
    fun callOrderExtra(activity: AppCompatActivity)
    fun getOrder(): MutableLiveData<Order>
    fun getProducts(): MutableLiveData<List<Product>>
    fun callProductsROOM(context: Context, lifecycle: Lifecycle)
    fun getProductsById(): MutableLiveData<List<Product>>
    fun callProductsByIdROOM(context: Context, lifecycle: Lifecycle, id: List<Int>)
    fun getClients(): MutableLiveData<List<Client>>
    fun callClientsROOM(context: Context, lifecycle: Lifecycle)
    fun getClientById(): MutableLiveData<Client>
    fun callClientById(context: Context, lifecycle: Lifecycle, id:Int)
}