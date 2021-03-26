package com.listen.to.miskiatty.viewmodel

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.listen.to.miskiatty.R
import com.listen.to.miskiatty.model.adapters.AdapterCustomContacts
import com.listen.to.miskiatty.model.adapters.AdapterCustomListener
import com.listen.to.miskiatty.model.repository.clients.ClientsAddRepositoryImpl
import com.listen.to.miskiatty.model.repository.clients.Contact

class ClientsAddViewModel: ViewModel(){

    private var contactsAdapter: AdapterCustomContacts? = null
    private var clientsAddRepository = ClientsAddRepositoryImpl()

    fun callContacts(appContext: Context, lifecycle: Lifecycle) =
            clientsAddRepository.callContacts(appContext, lifecycle)

    fun getContacts(): MutableLiveData<List<Contact>> = clientsAddRepository.getContacts()

    fun getCheckedContacts(): List<Contact>? = contactsAdapter?.getCheckedContactsList()

    fun getRecyclerContactsAdapter(): AdapterCustomContacts? = contactsAdapter

    fun setRecyclerContactsAdapter(){
        contactsAdapter = AdapterCustomContacts(this, R.layout.template_client_add)
    }

    fun setContactsInRecyclerAdapter(adapter: AdapterCustomContacts?, contacts: List<Contact>){
        if(adapter != null){
            adapter.setContactsList(contacts)
            adapter.notifyDataSetChanged()
        }
    }

    fun getContactAt(position: Int): Contact? =
            contactsAdapter?.getContactsList()?.get(position)

    fun searchContact(str: String){
        contactsAdapter?.search(str)
    }
}