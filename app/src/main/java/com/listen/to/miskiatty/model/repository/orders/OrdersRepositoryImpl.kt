package com.listen.to.miskiatty.model.repository.orders

import android.content.Context
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
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
        /*val order = Order(
                client = Client(
                        image = BitmapFactory.decodeResource(context.resources, R.drawable.default_client_96),
                        name = "Juan",
                        phone = "987541990",
                        address = "Surco",
                        orders = ArrayList(), ),
                address = "Surco",
                deliveryDate = "12/10/2004",
                state = "Entregado",
                totalPrice = 80f,
                products = listOf(Product(
                        image = BitmapFactory.decodeResource(context.resources, R.drawable.default_client_96),
                        name = "keke",
                        price = 20f,
                        cost = 15f,
                        recipe = "Ingredientes"
                )),
                productsQuantity = listOf(5)
        )*/
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
}