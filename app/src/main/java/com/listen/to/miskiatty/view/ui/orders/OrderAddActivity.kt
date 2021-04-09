package com.listen.to.miskiatty.view.ui.orders

import android.content.Context
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.listen.to.miskiatty.R
import com.listen.to.miskiatty.databinding.ActivityOrderAddBinding
import com.listen.to.miskiatty.model.database.Product
import com.listen.to.miskiatty.viewmodel.OrderAddViewModel

class OrderAddActivity : AppCompatActivity() {

    private var orderAddViewModel: OrderAddViewModel? = null
    private lateinit var binding: ActivityOrderAddBinding
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpBinding()
        setContentView(binding.root)

        setUpToolbar()
        setRecyclerProductsAdapter()
        setUpDropDownMenu()
        setUpDataUpdate()
    }

    override fun onStart() {
        super.onStart()
        binding.lifecycleOwner = this
    }

    private fun setUpBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_add)

        orderAddViewModel = ViewModelProvider
            .NewInstanceFactory()
            .create(OrderAddViewModel::class.java)

        binding.orderAddViewModel = orderAddViewModel
    }

    private fun setUpDropDownMenu(){
        with(orderAddViewModel){
            this?.let {
                callClients(this@OrderAddActivity, lifecycle)
                getClients().observe(this@OrderAddActivity, { clients ->
                    val clientsNames = ArrayList<String>()

                    for (client in clients)
                        clientsNames.add(client.name)

                    val arrayAdapter = ArrayAdapter(applicationContext, R.layout.dropdown_item, clientsNames)
                    binding.clientNameAutoComplete.setAdapter(arrayAdapter)

                    binding.clientNameAutoComplete.setOnItemClickListener { p0, p1, position, p3 ->
                        binding.address.editText?.setText(clients[position].address)
                        orderAddViewModel?.client = clients[position]
                    }
                })
            }
        }
    }

    private fun setUpDataUpdate(){
        with(orderAddViewModel){
            this?.let {
                if(intentHasData(this@OrderAddActivity)){
                    callOrder(this@OrderAddActivity)

                    getOrder().observe(this@OrderAddActivity, { order ->
                        it.getClientById(order.client)?.let{ client ->
                            it.client = client
                            binding.clientName.editText?.setText(client.name)
                            binding.address.editText?.setText(order.address)
                            binding.deliveryDate.editText?.setText(order.deliveryDate)
                            binding.state.editText?.setText(order.state)
                        }
                    })
                }

                callProducts(this@OrderAddActivity, lifecycle)
                getProducts().observe(this@OrderAddActivity, { products ->
                    setProductsInRecyclerAdapter(products)
                })
            }
        }
    }

    private fun setRecyclerProductsAdapter(){
        orderAddViewModel?.setRecyclerOrdersAdapter()
    }

    private fun setUpToolbar(){
        toolbar = binding.toolbarProductAdd
        toolbarNavigationOnClickListener()
    }

    private fun toolbarNavigationOnClickListener(){
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}