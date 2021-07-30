package com.listen.to.miskiatty.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Order(
    @PrimaryKey(autoGenerate = true)
        var id: Int = 0,
    val client: Int,
    val address: String,
    val deliveryDate: String,
    val state: String,
    var products: List<Int>,
    var productsQuantity: List<Int>,
    val totalPrice: Float,
    val profit: Float) : Serializable
