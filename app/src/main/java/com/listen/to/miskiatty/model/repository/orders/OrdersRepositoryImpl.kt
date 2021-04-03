package com.listen.to.miskiatty.model.repository.orders

import android.content.Context
import android.graphics.BitmapFactory
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.coroutineScope
import com.listen.to.miskiatty.R
import com.listen.to.miskiatty.model.database.Client
import com.listen.to.miskiatty.model.database.Order
import com.listen.to.miskiatty.model.database.Product
import com.listen.to.miskiatty.model.database.room.RoomDb
import kotlinx.coroutines.launch

class OrdersRepositoryImpl: OrdersRepository {
    private var orders = MutableLiveData<List<Order>>()

    override fun callOrdersROOM(context: Context, lifecycle: Lifecycle) {
        val db = RoomDb.getDatabase(context)

        lifecycle.coroutineScope.launch {
            val ordersRoom = db.orderDao().getAllOrders()
            orders.value = ordersRoom
        }
    }

    override fun getOrders(): MutableLiveData<List<Order>> = orders
}