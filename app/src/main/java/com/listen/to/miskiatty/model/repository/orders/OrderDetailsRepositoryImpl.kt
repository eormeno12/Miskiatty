package com.listen.to.miskiatty.model.repository.orders

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.coroutineScope
import com.listen.to.miskiatty.model.database.Client
import com.listen.to.miskiatty.model.database.Order
import com.listen.to.miskiatty.model.database.Product
import com.listen.to.miskiatty.model.database.room.RoomDb
import kotlinx.coroutines.launch

class OrderDetailsRepositoryImpl: OrderDetailsRepository{

    private val order = MutableLiveData<Order>()

    override fun callOrderRoom(activity: AppCompatActivity, lifecycle: Lifecycle) {
        val id = activity.intent
                .getIntExtra(
                        "com.listen.to.miskiatty.view.ui.orders.ID",
                        0)

        val db = RoomDb.getDatabase(activity.applicationContext)

        lifecycle.coroutineScope.launch {
            val orderRoom = db.orderDao().getOrderById(id)
            order.value = orderRoom
        }
    }

    override fun getOrder(): MutableLiveData<Order> = order

    private val client = MutableLiveData<Client>()

    override fun callClientByIdRoom(context: Context, lifecycle: Lifecycle, id: Int) {
        val db = RoomDb.getDatabase(context)

        lifecycle.coroutineScope.launch {
            val clientRoom = db.clientDao().getClientById(id)
            client.value = clientRoom
        }
    }

    override fun getClient(): MutableLiveData<Client> = client

    private val product = MutableLiveData<Product>()

    override fun callProductById(context: Context, lifecycle: Lifecycle, id: Int){
        val db = RoomDb.getDatabase(context)

        lifecycle.coroutineScope.launch {
            val productRoom = db.productDao().getProductById(id)
            product.value = productRoom
        }
    }

    override fun getProductById() = product
}
