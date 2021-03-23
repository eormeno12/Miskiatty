package com.listen.to.miskiatty.viewmodel

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.listen.to.miskiatty.R
import com.listen.to.miskiatty.model.adapters.AdapterCustomClients
import com.listen.to.miskiatty.model.adapters.AdapterCustomListener
import com.listen.to.miskiatty.model.database.Client
import com.listen.to.miskiatty.model.repository.clients.ClientRepositoryImpl

class ClientViewModel: ViewModel() {

    private val clientRepository: ClientRepositoryImpl = ClientRepositoryImpl()
    var clientsAdapter: AdapterCustomClients? = null
    private var _clientClicked = MutableLiveData<Client>()
    val clientClicked: LiveData<Client> = _clientClicked

    fun getRecyclerClientsAdapter(): AdapterCustomClients?{
        return clientsAdapter
    }

    fun setRecyclerClientsAdapter(){
        clientsAdapter = AdapterCustomClients(this, R.layout.template_product,
                object : AdapterCustomListener{
                    override fun onClickListener(position: Int) {
                        _clientClicked.value = getClientAt(position)
                    }
                })
    }

    fun callClients(appContext: Context, lifecycle: Lifecycle) =
            clientRepository.callClientROOM(appContext, lifecycle)


    fun getClients(): MutableLiveData<List<Client>> = clientRepository.getClients()

    fun callTopClients(appContext: Context, lifecycle: Lifecycle) =
            clientRepository.callTopClientsROOM(appContext, lifecycle)

    fun getTopClients(): MutableLiveData<List<Client>> = clientRepository.getTopClients()


    fun setClientsInRecyclerAdapter(clients: List<Client>){
        if(clientsAdapter != null){
            clientsAdapter?.setClientsList(clients)
            clientsAdapter?.notifyDataSetChanged()
        }
    }

    fun getClientAt(position: Int): Client? =
            clientsAdapter?.getClientsList()?.get(position)

    fun searchClient(str: String){
        clientsAdapter?.search(str)
    }
}