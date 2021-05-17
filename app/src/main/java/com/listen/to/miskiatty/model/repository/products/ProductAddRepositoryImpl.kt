package com.listen.to.miskiatty.model.repository.products

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.coroutineScope
import com.listen.to.miskiatty.model.database.Product
import com.listen.to.miskiatty.model.database.room.RoomDb
import kotlinx.coroutines.launch

class ProductAddRepositoryImpl: ProductAddRepository {

    private val product = MutableLiveData<Product>()
    private var productDetailsID: Int = 0


    override fun verifyIntentData(activity: AppCompatActivity): Boolean {
        productDetailsID = activity.intent
                .getIntExtra(
                        "com.listen.to.miskiatty.view.ui.products.ID", 0)

        return (productDetailsID != 0)
    }

    override fun callProductExtra(activity: AppCompatActivity, lifecycle: Lifecycle) {
        val db = RoomDb.getDatabase(activity.applicationContext)

        lifecycle.coroutineScope.launch {
            val productRoom = db.productDao().getProductById(productDetailsID)
            product.value = productRoom
        }
    }

    override fun getProduct(): MutableLiveData<Product> = product

    override fun insertProductRoom(context: Context, lifecycle: Lifecycle, product: Product) {
        val db = RoomDb.getDatabase(context)

        lifecycle.coroutineScope.launch{
            db.productDao().addProduct(product)
        }
    }

    override fun updateProductRoom(context: Context, lifecycle: Lifecycle, product: Product) {
        val db = RoomDb.getDatabase(context)

        lifecycle.coroutineScope.launch{
            db.productDao().updateProduct(product)
        }
    }
}