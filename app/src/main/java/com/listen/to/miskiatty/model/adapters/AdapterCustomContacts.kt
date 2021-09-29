package com.listen.to.miskiatty.model.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.listen.to.miskiatty.model.repository.clients.Contact
import com.listen.to.miskiatty.viewmodel.clients.ClientsAddViewModel
import java.util.*
import kotlin.collections.ArrayList

class AdapterCustomContacts (var clientsAddViewModel: ClientsAddViewModel,
                             private var resource: Int, ):
        RecyclerView.Adapter<AdapterCustomContacts.ViewHolder>() {

    private var contactsList = ArrayList<Contact>()
    private var copyContactsList: ArrayList<Contact>? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setContactsList(contacts: List<Contact>){
        contactsList.clear()
        contactsList.addAll(contacts)
        copyContactsList = ArrayList(contactsList)
        notifyDataSetChanged()
    }

    fun getContactsList(): List<Contact> = contactsList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(
                layoutInflater,
                viewType,
                parent,
                false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            setData(clientsAddViewModel, position)
        }
    }

    override fun getItemCount(): Int {
        return contactsList.count()
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutIdByPosition(position)
    }

    private fun getLayoutIdByPosition(position: Int): Int{
        return resource
    }

    @SuppressLint("NotifyDataSetChanged")
    fun search(str: String){
        if(copyContactsList != null){
            contactsList.clear()

            if(str.isEmpty()) {
                contactsList = ArrayList(copyContactsList)
                notifyDataSetChanged()
                return
            }

            val search = str.toLowerCase(Locale.ROOT)

            for(contact in copyContactsList!!){
                val name = contact.name.toLowerCase(Locale.ROOT)
                val phone = contact.phone.toLowerCase(Locale.ROOT)

                if(name.contains(search) || phone.contains(search)) contactsList.add(contact)
            }
        }

        notifyDataSetChanged()
    }

    class ViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root){

        private var binding: ViewDataBinding? = null
        init {
            this.binding = binding
        }

        fun setData(clientsAddViewModel: ClientsAddViewModel, position: Int){
            binding?.setVariable(BR.clientsAddViewModel, clientsAddViewModel)
            binding?.setVariable(BR.position, position)
            binding?.executePendingBindings()
        }
    }
}