package com.listen.to.miskiatty.model.database

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Client (
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0,
        val image: Bitmap,
        val name: String,
        val phone: String,
        val address: String,
        val orders: List<Order>) : Serializable