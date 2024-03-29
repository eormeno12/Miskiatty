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
            val clientsRoom = db.clientDao().getAllClients()
            val clientsRoomSorted = clientsRoom.sortedByDescending { it.orders.count() }

            if(clientsRoomSorted.count() > 3)
                topClients.value = clientsRoomSorted.slice(IntRange(0, 2))
            else
                topClients.value = clientsRoomSorted
        }
    }

    override fun getTopClients(): MutableLiveData<List<Client>> = topClients

    override fun deleteClientROOM(context: Context, lifecycle: Lifecycle, client: Client) {
        val db = RoomDb.getDatabase(context)

        lifecycle.coroutineScope.launch {
            for (id in client.orders){
                val order = db.orderDao().getOrderById(id)
                db.orderDao().deleteOrder(order)
            }

            db.clientDao().deleteClient(client)
        }
    }
}