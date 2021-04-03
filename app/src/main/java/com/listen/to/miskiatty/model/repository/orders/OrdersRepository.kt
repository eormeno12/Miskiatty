package com.listen.to.miskiatty.model.repository.orders

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import com.listen.to.miskiatty.model.database.Order

interface OrdersRepository {
    fun getOrders(): MutableLiveData<List<Order>>
    fun callOrdersROOM(context: Context, lifecycle: Lifecycle)
}