package com.listen.to.miskiatty.view.ui.clients

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.listen.to.miskiatty.R
import com.listen.to.miskiatty.databinding.ActivityClientDetailsBinding
import com.listen.to.miskiatty.databinding.ActivityProductDetailsBinding
import com.listen.to.miskiatty.view.ui.products.ProductAddActivity
import com.listen.to.miskiatty.viewmodel.ClientDetailsViewModel
import com.listen.to.miskiatty.viewmodel.ProductDetailsViewModel

class ClientDetailsActivity : AppCompatActivity() {
    private var clientDetailsViewModel: ClientDetailsViewModel? = null
    private lateinit var binding: ActivityClientDetailsBinding
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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_client_details)

        clientDetailsViewModel = ViewModelProvider
                .NewInstanceFactory()
                .create(ClientDetailsViewModel::class.java)

        binding.clientDetailsViewModel = clientDetailsViewModel
    }

    private fun callProduct(){
        clientDetailsViewModel?.callClient(this, lifecycle)
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
                        val client = clientDetailsViewModel?.getClient()?.value
                        putExtra("com.listen.to.miskiatty.view.ui.products.DETAILS",
                                client)
                    })

                    true
                }

                else -> false
            }
        }
    }
}