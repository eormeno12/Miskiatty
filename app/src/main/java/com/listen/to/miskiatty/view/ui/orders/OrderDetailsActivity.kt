package com.listen.to.miskiatty.view.ui.orders

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.listen.to.miskiatty.R
import com.listen.to.miskiatty.databinding.ActivityOrderDetailsBinding
import com.listen.to.miskiatty.model.database.Product
import com.listen.to.miskiatty.viewmodel.OrderDetailsViewModel

class OrderDetailsActivity : AppCompatActivity() {

    private var orderDetailsViewModel: OrderDetailsViewModel? = null
    private lateinit var binding: ActivityOrderDetailsBinding
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpBinding()
        setContentView(binding.root)

        setUpToolbar()
        setRecyclerProductsAdapter()
        setUpListUpdate()
    }

    override fun onStart() {
        super.onStart()
        binding.lifecycleOwner = this
        callOrder()
    }

    private fun setRecyclerProductsAdapter(){
        orderDetailsViewModel?.setRecyclerOrdersAdapter()
    }

    private fun setUpListUpdate(){
        orderDetailsViewModel?.getOrder()?.observe(this, {
            val products = ArrayList<Product>()

            for(product in it.products)
                products.add(product)

            orderDetailsViewModel?.setProductsInRecyclerAdapter(products)
        })
    }

    private fun setUpBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_details)

        orderDetailsViewModel = ViewModelProvider
                .NewInstanceFactory()
                .create(OrderDetailsViewModel::class.java)

        binding.orderDetailsViewModel = orderDetailsViewModel
    }

    private fun callOrder(){
        orderDetailsViewModel?.callOrder(this, lifecycle)
    }

    private fun setUpToolbar(){
        toolbar = binding.toolbarOrderDetails
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
                    startActivity(Intent(this, OrderAddActiviy::class.java).apply {
                        val order = orderDetailsViewModel?.getOrder()?.value
                        putExtra("com.listen.to.miskiatty.view.ui.orders.DETAILS",
                                order)
                    })

                    true
                }

                else -> false
            }
        }
    }
}