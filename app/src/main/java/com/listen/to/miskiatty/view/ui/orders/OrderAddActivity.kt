package com.listen.to.miskiatty.view.ui.orders

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.listen.to.miskiatty.R
import com.listen.to.miskiatty.databinding.ActivityOrderAddBinding
import com.listen.to.miskiatty.viewmodel.OrderAddViewModel

class OrderAddActivity : AppCompatActivity() {

    private var orderAddViewModel: OrderAddViewModel? = null
    private lateinit var binding: ActivityOrderAddBinding
    private lateinit var toolbar: Toolbar
    lateinit var nameArrayAdapter: ArrayAdapter<String>


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
        callData()
    }

    private fun setUpBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_add)

        orderAddViewModel = ViewModelProvider
            .NewInstanceFactory()
            .create(OrderAddViewModel::class.java)

        binding.orderAddViewModel = orderAddViewModel
    }

    private fun callData(){
        orderAddViewModel?.let {
            it.callClients(this@OrderAddActivity, lifecycle)
            it.callProducts(this@OrderAddActivity, lifecycle)
        }
    }

    private fun setUpDropDownMenu(){
        nameArrayAdapter = ArrayAdapter(applicationContext, R.layout.dropdown_item)

        with(orderAddViewModel){
            this?.let {
                getClients().observe(this@OrderAddActivity, { clients ->
                    val clientsNames = ArrayList<String>()

                    for (c in clients)
                        clientsNames.add(c.name)

                    nameArrayAdapter.addAll(clientsNames)
                    binding.clientNameAutoComplete.setAdapter(nameArrayAdapter)

                    binding.clientNameAutoComplete.setOnItemClickListener { p0, p1, position, p3 ->
                        binding.address.editText?.setText(clients[position].address)
                        orderAddViewModel?.client = clients[position]
                    }
                })

                val states = arrayListOf("En cola", "Preparando", "Listo", "Entregado")
                val statesArrayAdapter = ArrayAdapter(applicationContext, R.layout.dropdown_item, states)
                binding.clientStateAutoComplete.setAdapter(statesArrayAdapter)
            }
        }
    }

    private fun setUpDataUpdate(){
        with(orderAddViewModel){
            this?.let {
                getProducts().observe(this@OrderAddActivity, { products ->
                    setProductsInRecyclerAdapter(products)
                })

                if(intentHasData(this@OrderAddActivity)){
                    callOrder(this@OrderAddActivity)
                    getOrder().observe(this@OrderAddActivity, { order ->
                        callClientById(this@OrderAddActivity, lifecycle, order.client)
                        getClientById().observe(this@OrderAddActivity, { client ->
                            it.client = client
                            nameArrayAdapter.clear()
                            nameArrayAdapter.add(client.name)

                            binding.clientName.editText?.setText(client.name)
                            binding.address.editText?.setText(order.address)
                            binding.deliveryDate.editText?.setText(order.deliveryDate)
                            binding.state.editText?.setText(order.state)

                            setCheckedProducts(this@OrderAddActivity, lifecycle, order)
                        })
                    })
                }
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