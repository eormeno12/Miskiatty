package com.listen.to.miskiatty.model.repository.clients

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.coroutineScope
import com.listen.to.miskiatty.model.database.Client
import com.listen.to.miskiatty.model.database.room.RoomDb
import kotlinx.coroutines.launch

class ClientEditRepositoryImpl: ClientEditRepository {
    private val client = MutableLiveData<Client>()
    private var clientDetailsID: Int = 0

    override fun callClientExtra(activity: Activity, lifecycle: Lifecycle) {
        clientDetailsID = activity.intent
                .getIntExtra(
                        "com.listen.to.miskiatty.view.ui.clients.ID", 0)
        val db = RoomDb.getDatabase(activity.applicationContext)

        lifecycle.coroutineScope.launch {
            val clientRoom = db.clientDao().getClientById(clientDetailsID)
            client.value = clientRoom
        }
    }

    override fun getClient(): MutableLiveData<Client> = client

    override fun updateClientRoom(context: Context, lifecycle: Lifecycle, client: Client) {
        val db = RoomDb.getDatabase(context)

        lifecycle.coroutineScope.launch {
            db.clientDao().updateClient(client)
        }
    }
}