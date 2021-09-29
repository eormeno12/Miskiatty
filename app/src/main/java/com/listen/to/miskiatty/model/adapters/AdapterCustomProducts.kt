package com.listen.to.miskiatty.model.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.listen.to.miskiatty.model.database.Product
import com.listen.to.miskiatty.viewmodel.products.ProductViewModel
import java.util.*
import kotlin.collections.ArrayList

class AdapterCustomProducts(var productViewModel: ProductViewModel,
                            private var resource: Int,
                            private var adapterCustomListener: AdapterCustomListener):
        RecyclerView.Adapter<AdapterCustomProducts.ViewHolder>() {

    private var productsList = ArrayList<Product>()
    private var copyProductList: ArrayList<Product>? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setProductsList(products: List<Product>){
        productsList.clear()
        productsList.addAll(products)
        copyProductList = ArrayList(productsList)
        notifyDataSetChanged()
    }

    fun getProductList(): List<Product> = productsList

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

        holder.setDataCard(productViewModel, position)
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

    @SuppressLint("NotifyDataSetChanged")
    fun search(str: String){
        if(copyProductList != null){
            productsList.clear()

            if(str.isEmpty()) {
                productsList = ArrayList(copyProductList)
                notifyDataSetChanged()
                return
            }

            val search = str.toLowerCase(Locale.ROOT)

            for(product in copyProductList!!){
                val name = product.name.toLowerCase(Locale.ROOT)

                if(name.contains(search)) productsList.add(product)
            }
        }

        notifyDataSetChanged()
    }

    fun setUpProductDeleteSwiping(rv: RecyclerView){
        val itemTouchHelper = ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onSwiped(target: RecyclerView.ViewHolder, direction: Int) {
                val position = target.adapterPosition
                val product = getProductList()[position]
                val appCompat = rv.context as AppCompatActivity
                productViewModel.deleteProductROOM(rv.context, appCompat.lifecycle, product)
                productsList.removeAt(position)
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

        fun setDataCard(productViewModel: ProductViewModel, position: Int){
            binding?.setVariable(BR.productViewModel, productViewModel)
            binding?.setVariable(BR.position, position)
            binding?.executePendingBindings()
        }

    }
}