package com.listen.to.miskiatty.model.repository.orders

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import com.listen.to.miskiatty.model.database.Order

interface OrderDetailsRepository {
    fun callOrderRoom(activity: AppCompatActivity, lifecycle: Lifecycle)
    fun getOrder(): MutableLiveData<Order>
}