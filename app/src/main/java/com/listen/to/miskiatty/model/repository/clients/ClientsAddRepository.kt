package com.listen.to.miskiatty.model.repository.clients

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import com.listen.to.miskiatty.model.database.Client

interface ClientsAddRepository {
    fun callContacts(context: Context, lifecycle: Lifecycle)
    fun getContacts(): MutableLiveData<List<Contact>>
    fun insertClientsROOM(context: Context, lifecycle: Lifecycle, clients: List<Client>)
}