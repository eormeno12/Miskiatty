package com.listen.to.miskiatty.view.ui.clients

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.listen.to.miskiatty.R
import com.listen.to.miskiatty.databinding.ActivityClientDetailsBinding
import com.listen.to.miskiatty.viewmodel.ClientDetailsViewModel

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