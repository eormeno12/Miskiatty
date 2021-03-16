package com.listen.to.miskiatty.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0,
        val image: Int,
        val name: String,
        val price: Float,
        val cost: Float,
        val recipe: String)
