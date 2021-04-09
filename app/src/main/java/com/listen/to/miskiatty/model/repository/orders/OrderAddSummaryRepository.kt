package com.listen.to.miskiatty.model.repository.orders

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import com.listen.to.miskiatty.model.database.Client
import com.listen.to.miskiatty.model.database.Order
import com.listen.to.miskiatty.model.database.Product

interface OrderAddSummaryRepository {
    fun verifyIfIsAnUpdate(activity: AppCompatActivity): Boolean
    fun callOrderExtra(activity: AppCompatActivity)
    fun getOrder(): MutableLiveData<Order>
    fun insertOrderRoom(context: Context, lifecycle: Lifecycle, order: Order)
    fun updateOrderRoom(context: Context, lifecycle: Lifecycle, order: Order)
    fun callClientByIdRoom(context: Context, lifecycle: Lifecycle, id: Int)
    fun getClient(): MutableLiveData<Client>
    fun callProductsRoom(context: Context, lifecycle: Lifecycle)
    fun getProducts(): MutableLiveData<List<Product>>
}