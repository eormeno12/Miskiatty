package com.listen.to.miskiatty.viewmodel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.listen.to.miskiatty.R
import com.listen.to.miskiatty.model.adapters.AdapterCustomClients
import com.listen.to.miskiatty.model.adapters.AdapterCustomListener
import com.listen.to.miskiatty.model.database.Client
import com.listen.to.miskiatty.model.permissions.ReadContactsService
import com.listen.to.miskiatty.model.repository.clients.ClientRepositoryImpl
import com.listen.to.miskiatty.view.ui.clients.ClientsAddActivity
import com.listen.to.miskiatty.view.ui.clients.ClientsFragment

class ClientViewModel: ViewModel() {

    private val clientRepository: ClientRepositoryImpl = ClientRepositoryImpl()

    private var clientsAdapter: AdapterCustomClients? = null
    private var _clientClicked = MutableLiveData<Client>()
    val clientClicked: LiveData<Client> = _clientClicked

    private var topClientsAdapter: AdapterCustomClients? = null
    private var _topClientClicked = MutableLiveData<Client>()
    val topClientClicked: LiveData<Client> = _topClientClicked

    //Clients

    fun getRecyclerClientsAdapter(): AdapterCustomClients?{
        return clientsAdapter
    }

    fun setRecyclerClientsAdapter(){
        clientsAdapter = AdapterCustomClients(this, R.layout.template_client,
                object : AdapterCustomListener{
                    override fun onClickListener(position: Int) {
                        _clientClicked.value = getClientAt(position)
                    }
                })
    }

    fun callClients(appContext: Context, lifecycle: Lifecycle) =
            clientRepository.callClientROOM(appContext, lifecycle)

    fun getClients(): MutableLiveData<List<Client>> = clientRepository.getClients()

    fun getClientAt(position: Int): Client? =
            clientsAdapter?.getClientsList()?.get(position)

    //Top Clients

    fun getRecyclerTopClientsAdapter(): AdapterCustomClients?{
        return topClientsAdapter
    }

    fun setRecyclerTopClientsAdapter(){
        topClientsAdapter = AdapterCustomClients(this, R.layout.template_client,
                object : AdapterCustomListener{
                    override fun onClickListener(position: Int) {
                        _topClientClicked.value = getTopClientAt(position)
                    }
                })
    }

    fun callTopClients(appContext: Context, lifecycle: Lifecycle) =
            clientRepository.callTopClientsROOM(appContext, lifecycle)

    fun getTopClients(): MutableLiveData<List<Client>> = clientRepository.getTopClients()

    fun getTopClientAt(position: Int): Client? =
            clientsAdapter?.getClientsList()?.get(position)


    fun setClientsInRecyclerAdapter(adapter: AdapterCustomClients?, clients: List<Client>){
        if(adapter != null){
            adapter.setClientsList(clients)
            adapter.notifyDataSetChanged()
        }
    }

    fun searchClient(str: String){
        clientsAdapter?.search(str)
    }

    fun onClickAddClients(context: Context) {
        val activity = context as Activity
        val readContactsService = ReadContactsService(activity)

        with(readContactsService){
            if(validatePermission()){
                context.startActivity(Intent(context, ClientsAddActivity::class.java))
            }else{
                askPermission()
            }
        }
    }
}