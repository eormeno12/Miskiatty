package com.listen.to.miskiatty.view.ui.products

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.listen.to.miskiatty.R
import com.listen.to.miskiatty.databinding.ActivityLoginBinding
import com.listen.to.miskiatty.databinding.ActivityProductDetailsBinding
import com.listen.to.miskiatty.model.database.Product
import com.listen.to.miskiatty.viewmodel.LoginViewModel
import com.listen.to.miskiatty.viewmodel.ProductAddViewModel
import com.listen.to.miskiatty.viewmodel.ProductDetailsViewModel
import java.io.Serializable

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
                R.id.edit_product -> {
                    startActivity(Intent(this, ProductAddActivity::class.java).apply {
                        putExtra("com.listen.to.miskiatty.view.ui.products.ID",
                                productDetailsViewModel?.getProduct()?.value?.id)
                    })
                    true
                }

                else -> false
            }
        }
    }
}