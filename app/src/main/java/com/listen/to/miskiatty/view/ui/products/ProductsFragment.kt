package com.listen.to.miskiatty.view.ui.products

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.listen.to.miskiatty.R
import com.listen.to.miskiatty.databinding.FragmentProductsBinding
import com.listen.to.miskiatty.model.database.Product
import com.listen.to.miskiatty.viewmodel.ProductViewModel

class ProductsFragment : Fragment() {

    private var productViewModel: ProductViewModel? = null
    private lateinit var binding: FragmentProductsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setupBinding(inflater, container)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbarItemClickListener()
        setRecyclerProductsAdapter()
        setUpListUpdate()
    }

    private fun setupBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil
            .inflate(
                inflater,
                R.layout.fragment_products,
                container,
                false
            )

        productViewModel = ViewModelProvider
            .NewInstanceFactory()
            .create(ProductViewModel::class.java)
        binding.productViewModel = productViewModel
    }

    private fun toolbarItemClickListener(){
        binding.toolbarProducts.setOnMenuItemClickListener { menu ->
            when(menu.itemId){
                R.id.search_product -> {
                    true
                }

                R.id.add_product -> {
                    findNavController().navigate(R.id.productAddActivity)
                    true
                }

                else -> false
            }
        }
    }

    private fun setRecyclerProductsAdapter(){
        productViewModel?.setRecyclerProductsAdapter()
    }

    private fun setUpListUpdate(){
        this.context?.let {
            productViewModel?.callProducts(it, lifecycle)
        }

        productViewModel?.getProducts()?.observe(viewLifecycleOwner, {
            products: List<Product> ->
            Log.d("products", products.toString())
            productViewModel?.setProductsInRecyclerAdapter(products)
        })
    }
}