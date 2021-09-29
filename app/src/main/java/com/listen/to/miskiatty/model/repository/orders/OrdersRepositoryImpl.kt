package com.listen.to.miskiatty.model.repository.orders

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.coroutineScope
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
            //db.orderDao().addOrder(order)
            val ordersRoom = db.orderDao().getAllOrders()
            orders.value = ordersRoom
        }
    }

    override fun getOrders(): MutableLiveData<List<Order>> = orders

    private val clients = MutableLiveData<List<Client>>()

    override fun callClientsRoom(context: Context, lifecycle: Lifecycle) {
        val db = RoomDb.getDatabase(context)

        lifecycle.coroutineScope.launch {
            val clientsRoom = db.clientDao().getAllClients()
            clients.value = clientsRoom
        }
    }

    override fun getClient(id: Int): MutableLiveData<Client>{
        val clientLiveData = MutableLiveData<Client>()

        clients.value?.let {
            for (client in it)
                if(client.id == id) clientLiveData.value = client
        }

        return clientLiveData
    }

    private val products = MutableLiveData<List<Product>>()

    override fun callProductsRoom(context: Context, lifecycle: Lifecycle) {
        val db = RoomDb.getDatabase(context)

        lifecycle.coroutineScope.launch {
            val productsRoom = db.productDao().getAllProducts()
            products.value = productsRoom
        }
    }

    override fun getProducts(): MutableLiveData<List<Product>> = products

    override fun deleteOrderROOM(context: Context, lifecycle: Lifecycle, order: Order) {
        val db = RoomDb.getDatabase(context)

        lifecycle.coroutineScope.launch {
            val clients = db.clientDao().getAllClients()

            for (client in clients){
                val orders = arrayListOf<Int>()
                orders.addAll(client.orders)

                for (clientOrder in orders){
                    if(clientOrder == order.id){
                        orders.remove(order.id)
                    }
                }

                client.orders = orders
                db.clientDao().updateClient(client)
            }

            db.orderDao().deleteOrder(order)
        }
    }
}