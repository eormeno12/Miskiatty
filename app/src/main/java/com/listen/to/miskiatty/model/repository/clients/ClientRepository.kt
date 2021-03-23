package com.listen.to.miskiatty.model.repository.clients

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import com.listen.to.miskiatty.model.database.Client

interface ClientRepository {
    fun getClients(): MutableLiveData<List<Client>>
    fun callClientROOM(context: Context, lifecycle: Lifecycle)
    fun getTopClients(): MutableLiveData<List<Client>>
    fun callTopClientsROOM(context: Context, lifecycle: Lifecycle)
}