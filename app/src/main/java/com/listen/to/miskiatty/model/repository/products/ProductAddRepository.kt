package com.listen.to.miskiatty.model.repository.products

import android.content.Context
import androidx.lifecycle.Lifecycle
import com.listen.to.miskiatty.model.database.Product

interface ProductAddRepository {
    fun insertProductRoom(context: Context, lifecycle: Lifecycle, product: Product)
}