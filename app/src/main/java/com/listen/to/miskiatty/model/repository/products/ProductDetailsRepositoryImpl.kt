package com.listen.to.miskiatty.model.repository.products

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.coroutineScope
import com.listen.to.miskiatty.model.database.Product
import com.listen.to.miskiatty.model.database.room.RoomDb
import kotlinx.coroutines.launch

class ProductDetailsRepositoryImpl: ProductDetailsRepository {

    private val product = MutableLiveData<Product>()

    override fun callProduct(activity: AppCompatActivity, lifecycle: Lifecycle) {
        val id = activity.intent
                .getIntExtra(
                        "com.listen.to.miskiatty.view.ui.products.ID",
                        0)

        val db = RoomDb.getDatabase(activity.applicationContext)

        lifecycle.coroutineScope.launch {
            val productRoom = db.productDao().getProductById(id)
            product.value = productRoom
        }
    }

    override fun getProduct(): MutableLiveData<Product> = product

}