package com.listen.to.miskiatty.viewmodel

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import com.listen.to.miskiatty.model.database.Product
import com.listen.to.miskiatty.model.repository.products.ProductAddObservable

class ProductAddViewModel: ViewModel(){
    private val productAddObservable: ProductAddObservable = ProductAddObservable()

    fun addProductRoom(context: Context, lifecycle: Lifecycle, product: Product){
        productAddObservable.insertProductRoom(context, lifecycle, product)
    }
}