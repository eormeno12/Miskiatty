package com.listen.to.miskiatty.view.ui.products

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.listen.to.miskiatty.R
import com.listen.to.miskiatty.databinding.ActivityProductDetailsBinding
import com.listen.to.miskiatty.viewmodel.products.ProductDetailsViewModel

class ProductDetailsActivity : AppCompatActivity() {

    private var productDetailsViewModel: ProductDetailsViewModel? = null
    private lateinit var binding: ActivityProductDetailsBinding
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpBinding()
        setContentView(binding.root)

        setUpToolbar()
    }

    override fun onStart() {
        super.onStart()
        binding.lifecycleOwner = this
        callProduct()
    }

    private fun setUpBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_details)

        productDetailsViewModel = ViewModelProvider
                .NewInstanceFactory()
                .create(ProductDetailsViewModel::class.java)

        binding.productDetailsViewModel = productDetailsViewModel
    }

    private fun callProduct(){
        productDetailsViewModel?.callProduct(this, lifecycle)
    }

    private fun setUpToolbar(){
        toolbar = binding.toolbarProductDetails
        toolbarNavigationOnClickListener()
        toolbarItemOnClickListener()
    }

    private fun toolbarNavigationOnClickListener(){
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun toolbarItemOnClickListener(){
        toolbar.setOnMenuItemClickListener { menu ->
            when(menu.itemId){
                R.id.edit -> {
                    startActivity(Intent(this, ProductAddActivity::class.java).apply {
                        putExtra("com.listen.to.miskiatty.view.ui.products.ID",
                                productDetailsViewModel?.getProduct()?.value?.id)
                    })
                    finish()
                    true
                }

                else -> false
            }
        }
    }
}