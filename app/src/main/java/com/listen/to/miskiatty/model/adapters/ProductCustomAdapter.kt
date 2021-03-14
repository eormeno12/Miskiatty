package com.listen.to.miskiatty.model.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.listen.to.miskiatty.model.Product

class ProductCustomAdapter(): RecyclerView.Adapter<ProductCustomAdapter.ViewHolder>() {

    var products: List<Product>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(
                layoutInflater,
                viewType,
                parent,
                false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
            holder.set

    override fun getItemCount(): Int = products?.size ?: 0

    class ViewHolder(val binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root){
        fun setDataCard(){
            binding.setVariable(BR.)
        }

    }
}