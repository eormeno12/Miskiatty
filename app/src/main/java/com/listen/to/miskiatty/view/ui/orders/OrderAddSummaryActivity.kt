package com.listen.to.miskiatty.view.ui.orders

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.listen.to.miskiatty.R
import com.listen.to.miskiatty.databinding.ActivityOrderAddSummaryBinding
import com.listen.to.miskiatty.databinding.ActivityProductAddBinding
import com.listen.to.miskiatty.model.database.Product
import com.listen.to.miskiatty.view.ui.activities.MainActivity
import com.listen.to.miskiatty.viewmodel.OrderAddSummaryViewModel
import com.listen.to.miskiatty.viewmodel.ProductAddViewModel

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
        callData()
        setRecyclerProductsAdapter()
        setDataInRecyclerAdapter()
    }

    override fun onStart() {
        super.onStart()
        isAnUpdate = orderAddSummaryViewModel?.verifyIfIsAnUpdate(this) == true
    }

    private fun callData(){
        orderAddSummaryViewModel?.let {
            it.callOrder(this)
            it.getOrder().observe(this, { order ->
                it.callClientById(this, lifecycle, order.id)
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

    private fun setDataInRecyclerAdapter(){
        orderAddSummaryViewModel?.let {
            with(it){
                callOrder(this@OrderAddSummaryActivity)
                getOrder().observe(this@OrderAddSummaryActivity, {order ->
                    val productsId = order.products
                    val productsList = ArrayList<Product>()

                    callProducts(this@OrderAddSummaryActivity, lifecycle)
                    getProducts().observe(this@OrderAddSummaryActivity, { products ->
                        for (id in productsId)
                            for (product in products)
                                if (product.id == id) productsList.add(product)
                    })

                    setProductsInRecyclerAdapter(productsList)
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
                        }
                    })

                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                    true
                }

                else -> false
            }
        }
    }
}