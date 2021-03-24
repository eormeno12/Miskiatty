package com.listen.to.miskiatty.viewmodel

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.listen.to.miskiatty.model.database.Client
import com.listen.to.miskiatty.model.repository.clients.ClientDetailsRepository
import com.listen.to.miskiatty.model.repository.clients.ClientDetailsRepositoryImpl

class ClientDetailsViewModel: ViewModel() {
    private val clientDetailsRepository: ClientDetailsRepository = ClientDetailsRepositoryImpl()

    fun callClient(activity: AppCompatActivity, lifecycle: Lifecycle) =
            clientDetailsRepository.callClientRoom(activity, lifecycle)


    fun getClient(): LiveData<Client> =
            clientDetailsRepository.getClient()
}