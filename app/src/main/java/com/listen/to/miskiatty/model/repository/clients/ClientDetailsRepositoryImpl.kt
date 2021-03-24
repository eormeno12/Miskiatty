package com.listen.to.miskiatty.model.repository.clients

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.coroutineScope
import com.listen.to.miskiatty.model.database.Client
import com.listen.to.miskiatty.model.database.room.RoomDb
import kotlinx.coroutines.launch

class ClientDetailsRepositoryImpl: ClientDetailsRepository {

    private val client = MutableLiveData<Client>()

    override fun callClientRoom(activity: AppCompatActivity, lifecycle: Lifecycle) {
        val id = activity.intent
                .getIntExtra(
                        "com.listen.to.miskiatty.view.ui.clients.ID",
                        0)

        val db = RoomDb.getDatabase(activity.applicationContext)

        lifecycle.coroutineScope.launch {
            val clientRoom = db.clientDao().getClientById(id)
            client.value = clientRoom
        }
    }

    override fun getClient(): MutableLiveData<Client> = client
}