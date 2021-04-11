package com.listen.to.miskiatty.model.database

import androidx.room.*

@Dao
interface OrderDao {
    @Query("SELECT * FROM `Order`")
    suspend fun getAllOrders(): List<Order>

    @Query("SELECT * FROM `ORDER` WHERE id = :id")
    suspend fun getOrderById(id: Int): Order

    @Update
    suspend fun updateOrder(order: Order)

    @Insert
    suspend fun addOrder(order: Order)

    @Delete
    suspend fun deleteOrder(order: Order)
}