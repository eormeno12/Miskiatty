package com.listen.to.miskiatty.model.repository.clients

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import com.listen.to.miskiatty.model.database.Client

interface ClientDetailsRepository {
    fun callClientRoom(activity: AppCompatActivity, lifecycle: Lifecycle)
    fun getClient(): MutableLiveData<Client>
}