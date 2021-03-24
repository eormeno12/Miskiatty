package com.listen.to.miskiatty.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.text.DateFormat

@Entity
data class Order(
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0,
        val client: Client,
        val address: String,
        val deliveryDate: DateFormat,
        val state: String,
        val products: ArrayList<Product>) : Serializable
