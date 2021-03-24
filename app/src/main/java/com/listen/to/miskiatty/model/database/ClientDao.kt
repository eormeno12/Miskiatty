package com.listen.to.miskiatty.model.database

import androidx.room.*

@Dao
interface ClientDao {
    @Query("SELECT * FROM Client")
    suspend fun getAllClients(): List<Client>

    @Query("SELECT * FROM Client")
    suspend fun getTopClients(): List<Client>

    @Query("SELECT * FROM Client WHERE id = :id")
    suspend fun getClientById(id: Int): Client

    @Update
    suspend fun updateClient(client: Client)

    @Insert
    suspend fun addClient(client: Client)

    @Delete
    suspend fun deleteClient(client: Client)
}