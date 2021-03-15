package com.listen.to.miskiatty.model.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.listen.to.miskiatty.model.Product
import com.listen.to.miskiatty.viewmodel.ProductViewModel

class ProductCustomAdapter(var productViewModel: ProductViewModel,
                           var resource: Int):
        RecyclerView.Adapter<ProductCustomAdapter.ViewHolder>() {

    var products: List<Product>? = null

    fun setProductsList(products: List<Product>){
        this.products = products
    }

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
        holder.setDataCard(productViewModel, position)
    }

    override fun getItemCount(): Int = products?.size ?: 0

    override fun getItemViewType(position: Int): Int {
        return getLayoutIDForPosition()
    }

    fun getLayoutIDForPosition(): Int{
        return resource
    }

    class ViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root){

        private var binding: ViewDataBinding? = null
        init {
            this.binding = binding
        }

        fun setDataCard(productViewModel: ProductViewModel, position: Int){
            binding?.setVariable(BR.productViewModel, productViewModel)
            binding?.setVariable(BR.position, position)
        }

    }
}