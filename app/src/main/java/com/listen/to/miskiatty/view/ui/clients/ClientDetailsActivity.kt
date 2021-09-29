package com.listen.to.miskiatty.view.ui.clients

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.listen.to.miskiatty.R
import com.listen.to.miskiatty.databinding.ActivityClientDetailsBinding
import com.listen.to.miskiatty.viewmodel.clients.ClientDetailsViewModel

class ClientDetailsActivity : AppCompatActivity() {
    private var clientDetailsViewModel: ClientDetailsViewModel? = null
    private lateinit var binding: ActivityClientDetailsBinding
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpBinding()
        setContentView(binding.root)

        setUpToolbar()
        setRecyclerProductsAdapter()
        setUpDataUpdate()
    }

    override fun onStart() {
        super.onStart()
        binding.lifecycleOwner = this
        callClient()
    }

    private fun setUpBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_client_details)

        clientDetailsViewModel = ViewModelProvider
                .NewInstanceFactory()
                .create(ClientDetailsViewModel::class.java)

        binding.clientDetailsViewModel = clientDetailsViewModel
    }

    private fun setRecyclerProductsAdapter(){
        clientDetailsViewModel?.setRecyclerOrdersAdapter()
    }

    private fun setUpDataUpdate(){
        with(clientDetailsViewModel){
            this?.let {
                callProducts(this@ClientDetailsActivity, lifecycle)

                getClient().observe(this@ClientDetailsActivity, { client ->
                    callOrdersById(this@ClientDetailsActivity, lifecycle, client.orders)
                    getOrdersById().observe(this@ClientDetailsActivity, { orders ->
                        setOrdersInRecyclerAdapter(orders)
                    })
                })
            }
        }
    }

    private fun callClient(){
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
                    startActivity(Intent(this, ClientEditActivity::class.java).apply {
                        val id = clientDetailsViewModel?.getClient()?.value?.id
                        putExtra("com.listen.to.miskiatty.view.ui.clients.ID", id)
                    })

                    true
                }

                else -> false
            }
        }
    }
}