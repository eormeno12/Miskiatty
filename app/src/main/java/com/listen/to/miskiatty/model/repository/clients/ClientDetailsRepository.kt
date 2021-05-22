package com.listen.to.miskiatty.model.repository.clients

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.listen.to.miskiatty.model.database.Client
import com.listen.to.miskiatty.model.database.Order
import com.listen.to.miskiatty.model.database.Product

interface ClientDetailsRepository {
    fun callClientRoom(activity: AppCompatActivity, lifecycle: Lifecycle)
    fun getClient(): MutableLiveData<Client>
    fun callOrdersById(activity: AppCompatActivity, lifecycle: Lifecycle, id: List<Int>)
    fun getOrdersById(): LiveData<List<Order>>
    fun callProducts(context: Context, lifecycle: Lifecycle)
    fun getProducts(): LiveData<List<Product>>
}