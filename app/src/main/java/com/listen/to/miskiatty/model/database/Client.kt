package com.listen.to.miskiatty.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Client (
    @PrimaryKey(autoGenerate = true)
        val id: Int = 0,
    var image: String,
    val name: String,
    val phone: String,
    val address: String,
    var orders: List<Int>) : Serializable