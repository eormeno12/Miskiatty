package com.listen.to.miskiatty.model.database

import androidx.room.*

@Dao
interface ProductDao {
    @Query("SELECT * FROM Product")
    suspend fun getAllProducts(): List<Product>

    @Query("SELECT * FROM Product WHERE id = :id")
    suspend fun getProductById(id: Int): Product

    @Update
    suspend fun updateProduct(product: Product)

    @Insert
    suspend fun addProducts(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)
}