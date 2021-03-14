package com.listen.to.miskiatty.view.ui.products

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.listen.to.miskiatty.R
import com.listen.to.miskiatty.databinding.FragmentProductsBinding
import com.listen.to.miskiatty.viewmodel.ProductViewModel

class ProductsFragment : Fragment() {

    private var productViewModel: ProductViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupBinding(inflater, container)
        return inflater.inflate(R.layout.fragment_products, container, false)
    }

    fun setupBinding(inflater: LayoutInflater, container: ViewGroup?){
        val fragmentProductBinding: FragmentProductsBinding = DataBindingUtil
                .inflate(inflater,
                        R.layout.fragment_products,
                        container,
                        false)
        productViewModel = ViewModelProvider
                .NewInstanceFactory()
                .create(ProductViewModel::class.java)
        fragmentProductBinding.productViewModel = productViewModel
    }
}