package com.listen.to.miskiatty.model.repository.clients

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.coroutineScope
import com.listen.to.miskiatty.model.database.Client
import com.listen.to.miskiatty.model.database.Order
import com.listen.to.miskiatty.model.database.Product
import com.listen.to.miskiatty.model.database.room.RoomDb
import kotlinx.coroutines.launch

class ClientDetailsRepositoryImpl: ClientDetailsRepository {

    private val client = MutableLiveData<Client>()

    override fun callClientRoom(activity: AppCompatActivity, lifecycle: Lifecycle) {
        val id = activity.intent
                .getIntExtra(
                        "com.listen.to.miskiatty.view.ui.clients.ID",
                        0)

        val db = RoomDb.getDatabase(activity.applicationContext)

        lifecycle.coroutineScope.launch {
            val clientRoom = db.clientDao().getClientById(id)
            client.value = clientRoom
        }
    }

    override fun getClient(): MutableLiveData<Client> = client

    private val orders = MutableLiveData<List<Order>>()

    override fun callOrdersById(activity: AppCompatActivity, lifecycle: Lifecycle, id: List<Int>) {
        val db = RoomDb.getDatabase(activity.applicationContext)

        lifecycle.coroutineScope.launch {
            val ordersRoom = ArrayList<Order>()
            for (i in id)
                ordersRoom.add(db.orderDao().getOrderById(i))

            orders.value = ordersRoom
        }
    }

    override fun getOrdersById() = orders

    private val products = MutableLiveData<List<Product>>()

    override fun callProducts(context: Context, lifecycle: Lifecycle) {
        val db = RoomDb.getDatabase(context)

        lifecycle.coroutineScope.launch {
            products.value = db.productDao().getAllProducts()
        }
    }

    override fun getProducts(): LiveData<List<Product>> = products
}