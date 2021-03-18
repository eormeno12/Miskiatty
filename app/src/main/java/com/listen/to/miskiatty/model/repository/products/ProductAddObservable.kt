package com.listen.to.miskiatty.model.repository.products

import android.content.Context
import androidx.databinding.BaseObservable
import androidx.lifecycle.Lifecycle
import com.listen.to.miskiatty.model.database.Product

class ProductAddObservable: BaseObservable(){
    private val productAddRepository = ProductAddRepositoryImpl()

    fun insertProductRoom(context: Context, lifecycle: Lifecycle, product: Product){
        productAddRepository.insertProductRoom(context, lifecycle, product)
    }
}