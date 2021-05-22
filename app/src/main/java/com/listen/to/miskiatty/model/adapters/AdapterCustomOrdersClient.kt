package com.listen.to.miskiatty.model.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.listen.to.miskiatty.model.database.Order
import com.listen.to.miskiatty.viewmodel.ClientDetailsViewModel

class AdapterCustomOrdersClient (var clientDetailsViewModel: ClientDetailsViewModel,
                                            var resource: Int):
        RecyclerView.Adapter<AdapterCustomOrdersClient.ViewHolder>() {

    private var ordersList = ArrayList<Order>()

    fun setOrdersList(orders: List<Order>){
        ordersList.clear()
        ordersList.addAll(orders)
        notifyDataSetChanged()
    }

    fun getOrdersList(): List<Order> = ordersList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(
                layoutInflater,
                viewType,
                parent,
                false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setDataCard(clientDetailsViewModel, position)
    }

    override fun getItemCount(): Int {
        return ordersList.count()
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutIdByPosition(position)
    }

    private fun getLayoutIdByPosition(position: Int): Int{
        return resource
    }

    class ViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root){

        private var binding: ViewDataBinding? = null
        init {
            this.binding = binding
        }

        fun setDataCard(clientDetailsViewModel: ClientDetailsViewModel, position: Int){
            binding?.setVariable(BR.clientDetailsViewModel, clientDetailsViewModel)
            binding?.setVariable(BR.position, position)
            binding?.executePendingBindings()
        }

    }
}