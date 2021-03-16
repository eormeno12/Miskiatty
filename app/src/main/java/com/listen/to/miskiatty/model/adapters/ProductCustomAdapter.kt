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

    private var productsList = ArrayList<Product>()

    fun setProductsList(products: List<Product>){
        productsList.clear()
        productsList.addAll(products)
        this.notifyDataSetChanged()
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

    override fun getItemCount(): Int {
        return productsList.count()
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutIDForPosition(position)
    }

    fun getLayoutIDForPosition(position: Int): Int{
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
            binding?.executePendingBindings()
        }

    }
}