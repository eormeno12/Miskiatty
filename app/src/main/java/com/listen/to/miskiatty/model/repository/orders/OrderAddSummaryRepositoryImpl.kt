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

class OrderAddSummaryRepositoryImpl: OrderAddSummaryRepository {
    private val order = MutableLiveData<Order>()

    override fun verifyIfIsAnUpdate(activity: AppCompatActivity): Boolean =
        activity.intent.getBooleanExtra(
                "com.listen.to.miskiatty.view.ui.orders.UPDATE", false)

    override fun callOrderExtra(activity: AppCompatActivity) {
        val orderDetails = activity.intent
            .getSerializableExtra(
                "com.listen.to.miskiatty.view.ui.orders.DETAILS") as Order

        order.value = orderDetails
    }

    override fun getOrder(): MutableLiveData<Order> = order

    override fun insertOrderRoom(context: Context, lifecycle: Lifecycle, order: Order) {
        val db = RoomDb.getDatabase(context)

        lifecycle.coroutineScope.launch{
            db.orderDao().addOrder(order)
        }
    }

    override fun updateOrderRoom(context: Context, lifecycle: Lifecycle, order: Order) {
        val db = RoomDb.getDatabase(context)

        lifecycle.coroutineScope.launch{
            db.orderDao().updateOrder(order)
        }
    }

    private val client = MutableLiveData<Client>()

    override fun callClientByIdRoom(context: Context, lifecycle: Lifecycle, id: Int) {
        val db = RoomDb.getDatabase(context)

        lifecycle.coroutineScope.launch {
            val clientRoom = db.clientDao().getClientById(id)
            client.value = clientRoom
        }
    }

    override fun getClient(): MutableLiveData<Client> = client

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