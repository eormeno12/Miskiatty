package com.listen.to.miskiatty.viewmodel

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.listen.to.miskiatty.model.database.Client
import com.listen.to.miskiatty.model.database.Order
import com.listen.to.miskiatty.model.database.Product
import com.listen.to.miskiatty.model.repository.statistics.StatisticsRepository
import com.listen.to.miskiatty.model.repository.statistics.StatisticsRepositoryImpl

class StatisticsViewModel: ViewModel() {
    private val statisticsRepository: StatisticsRepository = StatisticsRepositoryImpl()

    fun callProductsById(context: Context, lifecycle: Lifecycle, id: List<Int>) {
        statisticsRepository.callProductsByIdRoom(context, lifecycle, id)
    }

    fun getProductsById(): LiveData<List<Product>> = statisticsRepository.getProductsById()

    fun callClients(context: Context, lifecycle: Lifecycle) {
        statisticsRepository.callClientsRoom(context, lifecycle)
    }

    fun getClients(): MutableLiveData<List<Client>> = statisticsRepository.getClients()

    fun callOrders(context: Context, lifecycle: Lifecycle) =
        statisticsRepository.callOrdersRoom(context, lifecycle)

    fun getOrders(): LiveData<List<Order>> =
        statisticsRepository.getOrders()
}