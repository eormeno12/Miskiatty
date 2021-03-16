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
            val product = Product(
                    image = 10,
                    name = "KEKE",
                    price = 30f,
                    cost = 10f,
                    recipe = "1. Cernir la harina")
            db.productDao().addProducts(listOf(product))

            val productsRoom = db.productDao().getAllProducts()
            products.value = productsRoom
        }
    }

    override fun getProducts(): MutableLiveData<List<Product>> {
        return products
    }

}