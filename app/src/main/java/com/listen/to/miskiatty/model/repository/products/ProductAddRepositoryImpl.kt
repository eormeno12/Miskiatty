package com.listen.to.miskiatty.model.repository.products

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import com.listen.to.miskiatty.model.database.Product
import com.listen.to.miskiatty.model.database.room.RoomDb
import kotlinx.coroutines.launch

class ProductAddRepositoryImpl: ProductAddRepository {

    override fun insertProductRoom(context: Context, lifecycle: Lifecycle, product: Product) {
        val db = RoomDb.getDatabase(context)

        lifecycle.coroutineScope.launch{
            db.productDao().addProducts(product)
        }
    }
}