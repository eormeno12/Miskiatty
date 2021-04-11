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

        callData()
        setUpToolbar()
        setRecyclerProductsAdapter()
        setUpListUpdate()
    }

    override fun onStart() {
        super.onStart()
        binding.lifecycleOwner = this
    }

    private fun setRecyclerProductsAdapter(){
        orderDetailsViewModel?.setRecyclerOrdersAdapter()
    }

    private fun setUpListUpdate(){
        orderDetailsViewModel?.let {
            with(it){
                callOrder(this@OrderDetailsActivity, lifecycle)
                getOrder().observe(this@OrderDetailsActivity, {order ->
                    val productsId = order.products
                    val productsList = ArrayList<Product>()

                    callProducts(this@OrderDetailsActivity, lifecycle)
                    getProducts().observe(this@OrderDetailsActivity, { products ->
                         for (id in productsId)
                             for (product in products)
                                 if (product.id == id) productsList.add(product)
                    })

                    orderDetailsViewModel?.setProductsInRecyclerAdapter(productsList)
                })
            }
        }
    }

    private fun setUpBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_details)

        orderDetailsViewModel = ViewModelProvider
                .NewInstanceFactory()
                .create(OrderDetailsViewModel::class.java)

        binding.orderDetailsViewModel = orderDetailsViewModel
    }

    private fun callData(){
        orderDetailsViewModel?.let{
            it.getOrder().observe(this, { order ->
                it.callClientById(this, lifecycle, order.id)
            })
        }
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
                    startActivity(Intent(this, OrderAddActivity::class.java).apply {
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