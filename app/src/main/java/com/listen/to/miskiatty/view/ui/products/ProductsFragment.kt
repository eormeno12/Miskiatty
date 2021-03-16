package com.listen.to.miskiatty.view.ui.products

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.listen.to.miskiatty.R
import com.listen.to.miskiatty.databinding.FragmentProductsBinding
import com.listen.to.miskiatty.model.Product
import com.listen.to.miskiatty.model.adapters.ProductCustomAdapter
import com.listen.to.miskiatty.viewmodel.ProductViewModel

class ProductsFragment : Fragment() {

    private var productViewModel: ProductViewModel? = null
    lateinit var binding: FragmentProductsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setupBinding(inflater, container)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerProductsAdapter()
        setUpListUpdate()
    }

    fun setupBinding(inflater: LayoutInflater, container: ViewGroup?) {
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

    fun setRecyclerProductsAdapter(){
        productViewModel?.setRecyclerProductsAdapter()
    }


    fun setUpListUpdate(){
        productViewModel?.callProducts()
        productViewModel?.getProducts()?.observe(viewLifecycleOwner, Observer<List<Product>>  {
            products: List<Product> ->
            Log.d("products", products.toString())
            productViewModel?.setProductsInRecyclerAdapter(products)
        })
    }
}