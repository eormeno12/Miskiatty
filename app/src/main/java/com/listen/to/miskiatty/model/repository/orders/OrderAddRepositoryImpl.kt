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
import java.io.Serializable

class OrderAddRepositoryImpl: OrderAddRepository {

    private val order = MutableLiveData<Order>()
    private var orderDetailsSerializable: Serializable? = null

    private val products = MutableLiveData<List<Product>>()
    private val clients = MutableLiveData<List<Client>>()

    override fun verifyIntentData(activity: AppCompatActivity): Boolean {
        orderDetailsSerializable = activity.intent
            .getSerializableExtra(
                "com.listen.to.miskiatty.view.ui.orders.DETAILS")

        return (orderDetailsSerializable != null)
    }

    override fun callOrderExtra(activity: AppCompatActivity) {
        val orderDetails: Order = orderDetailsSerializable as Order
        order.value = orderDetails
    }

    override fun getOrder(): MutableLiveData<Order> = order

    override fun callProductsROOM(context: Context, lifecycle: Lifecycle) {
        val db = RoomDb.getDatabase(context)

        lifecycle.coroutineScope.launch {
            val productsRoom = db.productDao().getAllProducts()
            products.value = productsRoom
        }
    }

    override fun getProducts(): MutableLiveData<List<Product>> = products

    override fun callClientsROOM(context: Context, lifecycle: Lifecycle) {
        val db = RoomDb.getDatabase(context)

        lifecycle.coroutineScope.launch {
            val clientRoom = db.clientDao().getAllClients()
            clients.value = clientRoom
        }
    }

    override fun getClients(): MutableLiveData<List<Client>> = clients
}


