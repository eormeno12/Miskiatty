package com.listen.to.miskiatty.model.repository.statistics

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.coroutineScope
import com.listen.to.miskiatty.model.database.Client
import com.listen.to.miskiatty.model.database.Order
import com.listen.to.miskiatty.model.database.Product
import com.listen.to.miskiatty.model.database.room.RoomDb
import kotlinx.coroutines.launch

class StatisticsRepositoryImpl: StatisticsRepository {

    private val products = MutableLiveData<List<Product>>()
    private val clients = MutableLiveData<List<Client>>()
    private val orders = MutableLiveData<List<Order>>()

    override fun callProductsByIdRoom(context: Context, lifecycle: Lifecycle, id: List<Int>) {
        val db = RoomDb.getDatabase(context)

        lifecycle.coroutineScope.launch {
            val productsRoom = ArrayList<Product>()

            for(i in id)
                productsRoom.add(db.productDao().getProductById(i))

            products.value = productsRoom
        }
    }

    override fun getProductsById(): MutableLiveData<List<Product>> = products

    override fun callClientsRoom(context: Context, lifecycle: Lifecycle) {
        val db = RoomDb.getDatabase(context)

        lifecycle.coroutineScope.launch {
            val clientsRoom = db.clientDao().getAllClients()
            clients.value = clientsRoom
        }
    }

    override fun getClients(): MutableLiveData<List<Client>> = clients

    override fun callOrdersRoom(context: Context, lifecycle: Lifecycle) {
        val db = RoomDb.getDatabase(context)

        lifecycle.coroutineScope.launch {
            val ordersRoom = db.orderDao().getAllOrders()
            orders.value = ordersRoom
        }
    }

    override fun getOrders(): MutableLiveData<List<Order>> = orders
}