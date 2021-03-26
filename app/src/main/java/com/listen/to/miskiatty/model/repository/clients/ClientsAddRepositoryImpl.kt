package com.listen.to.miskiatty.model.repository.clients

import android.content.Context
import android.provider.ContactsContract
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.launch

class ClientsAddRepositoryImpl: ClientsAddRepository{

    private val mutableContacts = MutableLiveData<List<Contact>>()

    override fun callContacts(context: Context, lifecycle: Lifecycle) {
        lifecycle.coroutineScope.launch {
            val cursor = context.contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    null,
                    null,
                    null)

            val contacts = ArrayList<Contact>()

            cursor?.let {
                while (it.moveToNext()){
                    val name = it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                    val phone = it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    val contact = Contact(name, phone)

                    contacts.add(contact)
                }

                mutableContacts.value = contacts
            }
        }
    }

    override fun getContacts(): MutableLiveData<List<Contact>> = mutableContacts

}