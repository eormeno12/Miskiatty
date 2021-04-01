package com.listen.to.miskiatty.model.repository.clients

import android.app.Activity
import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import com.listen.to.miskiatty.model.database.Client

interface ClientEditRepository {
    fun callClientExtra(activity: Activity, lifecycle: Lifecycle)
    fun getClient(): MutableLiveData<Client>
    fun updateClientRoom(context: Context, lifecycle: Lifecycle, client: Client)
}