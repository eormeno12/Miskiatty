package com.listen.to.miskiatty.model.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.ItemTouchHelper
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
                    val orderDate = order.deliveryDate

                    if(clientName.contains(search) || orderDate.contains(search))
                        ordersList.add(order)
                }
            }
        }

        notifyDataSetChanged()
    }

    fun setUpOrderDeleteSwiping(rv: RecyclerView){
        val itemTouchHelper = ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(target: RecyclerView.ViewHolder, direction: Int) {
                val position = target.adapterPosition
                val order = getOrdersList()[position]
                val appCompat = rv.context as AppCompatActivity
                orderViewModel.deleteOrderROOM(rv.context, appCompat.lifecycle, order)
                notifyDataSetChanged()
            }

        })

        itemTouchHelper.attachToRecyclerView(rv)
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