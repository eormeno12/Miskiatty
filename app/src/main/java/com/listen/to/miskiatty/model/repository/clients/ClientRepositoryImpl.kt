package com.listen.to.miskiatty.model.repository.clients

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.coroutineScope
import com.listen.to.miskiatty.model.database.Client
import com.listen.to.miskiatty.model.database.room.RoomDb
import kotlinx.coroutines.launch

class ClientRepositoryImpl: ClientRepository {
    private var clients = MutableLiveData<List<Client>>()
    private var topClients = MutableLiveData<List<Client>>()

    override fun getClients(): MutableLiveData<List<Client>> = clients

    override fun callClientROOM(context: Context, lifecycle: Lifecycle) {
        val db = RoomDb.getDatabase(context)

        lifecycle.coroutineScope.launch {
            val clientsRoom = db.clientDao().getAllClients()
            clients.value = clientsRoom
        }
    }

    override fun callTopClientsROOM(context: Context, lifecycle: Lifecycle) {
        val db = RoomDb.getDatabase(context)

        lifecycle.coroutineScope.launch {
            val topClientsRoom = db.clientDao().getTopClients()
            topClients.value = topClientsRoom
        }
    }

    override fun getTopClients(): MutableLiveData<List<Client>> = topClients
}