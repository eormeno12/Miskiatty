package com.listen.to.miskiatty.model.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.listen.to.miskiatty.model.database.Product
import com.listen.to.miskiatty.viewmodel.OrderAddSummaryViewModel

class AdapterCustomAddSummaryOrderProducts (var orderAddSummaryViewModel: OrderAddSummaryViewModel,
                                            var resource: Int):
    RecyclerView.Adapter<AdapterCustomAddSummaryOrderProducts.ViewHolder>() {

    private var productsList = ArrayList<Product>()

    fun setProductsList(products: List<Product>){
        productsList.clear()
        productsList.addAll(products)
        Log.d("products order", productsList.toString())
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
        holder.setDataCard(orderAddSummaryViewModel, position)
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

        fun setDataCard(orderAddSummaryViewModel: OrderAddSummaryViewModel, position: Int){
            binding?.setVariable(BR.orderAddSummaryViewModel, orderAddSummaryViewModel)
            binding?.setVariable(BR.position, position)
            binding?.executePendingBindings()
        }

    }
}