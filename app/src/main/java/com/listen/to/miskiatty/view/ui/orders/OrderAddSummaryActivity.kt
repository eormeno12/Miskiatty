package com.listen.to.miskiatty.view.ui.orders

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.listen.to.miskiatty.R
import com.listen.to.miskiatty.databinding.ActivityOrderAddSummaryBinding
import com.listen.to.miskiatty.view.ui.activities.MainActivity
import com.listen.to.miskiatty.viewmodel.orders.OrderAddSummaryViewModel

class OrderAddSummaryActivity : AppCompatActivity() {
    private var orderAddSummaryViewModel: OrderAddSummaryViewModel? = null
    private var isAnUpdate: Boolean = false
    private lateinit var binding: ActivityOrderAddSummaryBinding
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_add_summary)

        setupBinding()
        setUpToolbar()
        setRecyclerProductsAdapter()
        setDataInRecyclerAdapter()
        setClientUpdate()
    }

    override fun onStart() {
        super.onStart()
        isAnUpdate = orderAddSummaryViewModel?.verifyIfIsAnUpdate(this) == true
        callData()
    }

    private fun callData(){
        orderAddSummaryViewModel?.let {
            it.callOrder(this)

            it.getOrder().observe(this, { order ->
                if (order != null)
                    it.callClientById(this, lifecycle, order.client)
            })
        }
    }

    private fun setupBinding(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_add_summary)
        orderAddSummaryViewModel = ViewModelProvider
            .NewInstanceFactory()
            .create(OrderAddSummaryViewModel::class.java)

        binding.orderAddSummaryViewModel = orderAddSummaryViewModel
    }

    private fun setUpToolbar(){
        toolbar = binding.toolbarProductAddSummary
        toolbarNavigationOnClickListener()
        toolbarItemOnClickListener()
    }

    private fun toolbarNavigationOnClickListener(){
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setRecyclerProductsAdapter(){
        orderAddSummaryViewModel?.setRecyclerOrdersAdapter()
    }

    private fun setClientUpdate(){
        orderAddSummaryViewModel?.getClientById()?.observe(this, {
            if(it != null){
                binding.tvClient.text = it.name
            }
        })
    }

    private fun setDataInRecyclerAdapter(){
        orderAddSummaryViewModel?.let {
            with(it){
                getOrder().observe(this@OrderAddSummaryActivity, {order ->
                    val productsId = order.products

                    orderAddSummaryViewModel?.callProductsById(
                            this@OrderAddSummaryActivity, lifecycle, productsId)

                    orderAddSummaryViewModel?.getProductsById()?.observe(this@OrderAddSummaryActivity, { products ->
                        if (products != null)
                            setProductsInRecyclerAdapter(products)
                    })
                })
            }
        }
    }

    private fun toolbarItemOnClickListener(){
        toolbar.setOnMenuItemClickListener { menu ->
            when(menu.itemId){
                R.id.confirm_order -> {
                    orderAddSummaryViewModel?.getOrder()?.observe(this, {
                        if (isAnUpdate){
                            orderAddSummaryViewModel?.updateOrderRoom(applicationContext,
                                lifecycle, it)

                        } else {
                            orderAddSummaryViewModel?.addOrderRoom(applicationContext,
                                lifecycle, it)

                            orderAddSummaryViewModel?.updateClientRoom(applicationContext,
                                    lifecycle, it.client)
                        }
                    })

                    startActivity(Intent(this, MainActivity::class.java).addFlags(
                        Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    )

                    finish()
                    true
                }

                else -> false
            }
        }
    }
}