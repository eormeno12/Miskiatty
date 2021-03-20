package com.listen.to.miskiatty.model.repository.products

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.coroutineScope
import com.listen.to.miskiatty.model.database.Product
import com.listen.to.miskiatty.model.database.room.RoomDb
import kotlinx.coroutines.launch
import java.io.Serializable

class ProductAddRepositoryImpl: ProductAddRepository {

    private val product = MutableLiveData<Product>()
    private var productDetailsSerializable: Serializable? = null


    override fun verifyIntentData(activity: AppCompatActivity): Boolean {
        productDetailsSerializable = activity.intent
                .getSerializableExtra(
                        "com.listen.to.miskiatty.view.ui.products.DETAILS")

        return (productDetailsSerializable != null)
    }

    override fun callProductExtra(activity: AppCompatActivity) {
        val productDetails: Product = productDetailsSerializable as Product
        product.value = productDetails
    }

    override fun getProduct(): MutableLiveData<Product> = product

    override fun insertProductRoom(context: Context, lifecycle: Lifecycle, product: Product) {
        val db = RoomDb.getDatabase(context)

        lifecycle.coroutineScope.launch{
            db.productDao().addProducts(product)
        }
    }

    override fun updateProductRoom(context: Context, lifecycle: Lifecycle, product: Product) {
        val db = RoomDb.getDatabase(context)

        lifecycle.coroutineScope.launch{
            db.productDao().updateProduct(product)
        }
    }
}