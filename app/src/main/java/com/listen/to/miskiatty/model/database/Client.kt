package com.listen.to.miskiatty.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Client (
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0,
        val name: String,
        val number: Int,
        val address: String,
        val orders: ArrayList<Order>) : Serializable