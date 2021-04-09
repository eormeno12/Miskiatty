package com.listen.to.miskiatty.model.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.listen.to.miskiatty.model.database.Product
import com.listen.to.miskiatty.viewmodel.OrderAddViewModel

class AdapterCustomAddOrderProducts  (var orderAddViewModel: OrderAddViewModel,
                                      var resource: Int):
    RecyclerView.Adapter<AdapterCustomAddOrderProducts.ViewHolder>() {

    private var productsList = ArrayList<Product>()
    private var copyProductsList: ArrayList<Product>? = null

    fun setProductsList(products: List<Product>){
        productsList.clear()
        productsList.addAll(products)
        copyProductsList = ArrayList(productsList)
        notifyDataSetChanged()
    }

    fun getProductsList(): List<Product> = productsList

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
        holder.setDataCard(orderAddViewModel, position)
    }

    override fun getItemCount(): Int {
        return productsList.count()
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

        fun setDataCard(orderAddViewModel: OrderAddViewModel, position: Int){
            binding?.setVariable(BR.orderAddViewModel, orderAddViewModel)
            binding?.setVariable(BR.position, position)
            binding?.executePendingBindings()
        }

    }
}