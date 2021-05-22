package com.listen.to.miskiatty.model.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.listen.to.miskiatty.R
import com.listen.to.miskiatty.model.database.Client
import com.listen.to.miskiatty.viewmodel.ClientViewModel
import java.util.*
import kotlin.collections.ArrayList

class AdapterCustomClients(var clientViewModel: ClientViewModel,
                           var resource: Int,
                           var clientsListener: AdapterCustomListener, private val withBgColor: Boolean):
        RecyclerView.Adapter<AdapterCustomClients.ViewHolder>() {

    private var clientsList = ArrayList<Client>()
    private var copyClientsList: ArrayList<Client>? = null
    private val colors: ArrayList<Int> = ArrayList()

    fun setClientsList(products: List<Client>){
        clientsList.clear()
        clientsList.addAll(products)
        copyClientsList = ArrayList(clientsList)
        notifyDataSetChanged()
    }

    fun getClientsList(): List<Client> = clientsList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(
                layoutInflater,
                viewType,
                parent,
                false)

        colors.add(R.color.colorPrimaryDark)
        colors.add(R.color.colorPrimary)
        colors.add(R.color.colorAccent)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            clientsListener.onClickListener(position)
        }

        if(withBgColor)
            holder.setDataCardWithBg(clientViewModel, position, colors[position])
        else
            holder.setDataCard(clientViewModel, position)
    }

    override fun getItemCount(): Int {
        return clientsList.count()
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutIdByPosition(position)
    }

    private fun getLayoutIdByPosition(position: Int): Int{
        return resource
    }

    fun search(str: String){
        if(copyClientsList != null){
            clientsList.clear()

            if(str.isEmpty()) {
                clientsList = ArrayList(copyClientsList)
                notifyDataSetChanged()
                return
            }

            val search = str.toLowerCase(Locale.ROOT)

            for(product in copyClientsList!!){
                val name = product.name.toLowerCase(Locale.ROOT)

                if(name.contains(search)) clientsList.add(product)
            }
        }

        notifyDataSetChanged()
    }

    class ViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root){

        private var binding: ViewDataBinding? = null
        init {
            this.binding = binding
        }

        fun setDataCard(clientViewModel: ClientViewModel, position: Int){
            binding?.setVariable(BR.clientViewModel, clientViewModel)
            binding?.setVariable(BR.position, position)
            binding?.executePendingBindings()
        }

        fun setDataCardWithBg(clientViewModel: ClientViewModel, position: Int, color: Int){
            binding?.setVariable(BR.clientViewModel, clientViewModel)
            binding?.setVariable(BR.position, position)
            binding?.setVariable(BR.bgColor, binding?.root?.context?.applicationContext?.resources?.getColor(color))
            binding?.executePendingBindings()
        }
    }
}