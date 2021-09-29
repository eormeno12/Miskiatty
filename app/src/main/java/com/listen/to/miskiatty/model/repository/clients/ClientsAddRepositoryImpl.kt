package com.listen.to.miskiatty.model.repository.clients

import android.content.Context
import android.provider.ContactsContract
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.coroutineScope
import com.listen.to.miskiatty.model.database.Client
import com.listen.to.miskiatty.model.database.room.RoomDb
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
                cursor.close()
            }
        }
    }

    override fun getContacts(): MutableLiveData<List<Contact>> = mutableContacts

    override fun insertClientsROOM(context: Context, lifecycle: Lifecycle, clients: List<Client>) {
        val db = RoomDb.getDatabase(context)

        lifecycle.coroutineScope.launch{
            db.clientDao().addClientsList(clients)
        }
    }

}