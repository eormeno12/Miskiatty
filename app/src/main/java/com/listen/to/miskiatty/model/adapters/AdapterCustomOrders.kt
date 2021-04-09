package com.listen.to.miskiatty.model.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.listen.to.miskiatty.model.database.Order
import com.listen.to.miskiatty.viewmodel.OrderViewModel
import java.util.*
import kotlin.collections.ArrayList

class AdapterCustomOrders (var orderViewModel: OrderViewModel,
                           var resource: Int,
                           var adapterCustomListener: AdapterCustomListener):
        RecyclerView.Adapter<AdapterCustomOrders.ViewHolder>() {

    private var ordersList = ArrayList<Order>()
    private var copyOrdersList: ArrayList<Order>? = null

    fun setOrdersList(orders: List<Order>){
        ordersList.clear()
        ordersList.addAll(orders)
        copyOrdersList = ArrayList(ordersList)
        notifyDataSetChanged()
    }

    fun getOrdersList(): List<Order> = ordersList

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
        holder.itemView.setOnClickListener {
            adapterCustomListener.onClickListener(position)
        }

        holder.setDataCard(orderViewModel, position)
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

    fun search(str: String){
        if(copyOrdersList != null){
            ordersList.clear()

            if(str.isEmpty()) {
                ordersList = ArrayList(copyOrdersList)
                notifyDataSetChanged()
                return
            }

            val search = str.toLowerCase(Locale.ROOT)

            for(order in copyOrdersList!!){
                orderViewModel.getClientById(order.client).value?.let {
                    val clientName = it.name.toLowerCase(Locale.ROOT)

                    if(clientName.contains(search)) ordersList.add(order)                }
            }
        }

        notifyDataSetChanged()
    }

    class ViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root){

        private var binding: ViewDataBinding? = null
        init {
            this.binding = binding
        }

        fun setDataCard(orderViewModel: OrderViewModel, position: Int){
            binding?.setVariable(BR.orderViewModel, orderViewModel)
            binding?.setVariable(BR.position, position)
            binding?.executePendingBindings()
        }

    }
}