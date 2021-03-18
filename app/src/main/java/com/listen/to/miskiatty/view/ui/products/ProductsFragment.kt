package com.listen.to.miskiatty.view.ui.products

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
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
        setUpBinding(inflater, container)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbarItemClickListener()
        setRecyclerProductsAdapter()
        setUpListUpdate()
        setUpOnClickProduct()
    }

    override fun onStart() {
        super.onStart()
        callProducts()
    }

    private fun setUpBinding(inflater: LayoutInflater, container: ViewGroup?) {
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
        productViewModel?.getProducts()?.observe(viewLifecycleOwner, {
            products: List<Product> ->
            Log.d("products", products.toString())
            productViewModel?.setProductsInRecyclerAdapter(products)
        })
    }

    private fun callProducts(){
        this.context?.let {
            productViewModel?.callProducts(it, lifecycle)
        }
    }

    private fun setUpOnClickProduct(){
        productViewModel?.productClicked?.observe(viewLifecycleOwner, {
            val bundle = bundleOf("product" to it)
            findNavController().navigate(R.id.productDetailsActivity, bundle)
        })
    }
}