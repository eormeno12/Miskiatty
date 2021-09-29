package com.listen.to.miskiatty.model.repository.products

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.coroutineScope
import com.listen.to.miskiatty.model.database.Product
import com.listen.to.miskiatty.model.database.room.RoomDb
import kotlinx.coroutines.launch

class ProductRepositoryImpl: ProductRepository {

    private var products = MutableLiveData<List<Product>>()

    override fun callProductsROOM(context: Context, lifecycle: Lifecycle) {
        val db = RoomDb.getDatabase(context)

        lifecycle.coroutineScope.launch {
            val productsRoom = db.productDao().getAllProducts()
            products.value = productsRoom
        }
    }

    override fun deleteProductROOM(context: Context, lifecycle: Lifecycle, product: Product) {
        val db = RoomDb.getDatabase(context)

        lifecycle.coroutineScope.launch {
            db.productDao().deleteProduct(product)
        }

        lifecycle.coroutineScope.launch {
            val orders = db.orderDao().getAllOrders()

            for (order in orders){
                val products = arrayListOf<Int>()
                products.addAll(order.products)

                val productsQuantity = arrayListOf<Int>()
                productsQuantity.addAll(order.productsQuantity)

                for (productOrder in products){
                    if(productOrder == product.id){
                        productsQuantity.removeAt(products.indexOf(productOrder))
                        products.remove(productOrder)
                    }
                }

                order.productsQuantity = productsQuantity
                order.products = products
                db.orderDao().updateOrder(order)
            }

            db.productDao().deleteProduct(product)
        }
    }

    override fun getProducts(): MutableLiveData<List<Product>> {
        return products
    }

}