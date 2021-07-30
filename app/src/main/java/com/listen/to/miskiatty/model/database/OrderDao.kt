package com.listen.to.miskiatty.model.database

import androidx.room.*

@Dao
interface OrderDao {
    @Query("SELECT * FROM `Order`")
    suspend fun getAllOrders(): List<Order>

    @Query("SELECT * FROM `ORDER` WHERE id = :id")
    suspend fun getOrderById(id: Int): Order

    @Query("SELECT * FROM `ORDER` ORDER BY id DESC LIMIT 1")
    suspend fun getLastOrder(): Order

    @Update
    suspend fun updateOrder(order: Order)

    @Insert
    suspend fun addOrder(order: Order)

    @Insert
    suspend fun addOrdersList(orders: List<Order>)

    @Delete
    suspend fun deleteOrder(order: Order)
}
