package com.listen.to.miskiatty.model.database

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Product(
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0,
        val image: Bitmap,
        val name: String,
        val price: Float,
        val cost: Float,
        val recipe: String)
        : Serializable
