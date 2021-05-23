package com.listen.to.miskiatty.view.ui.orders

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.listen.to.miskiatty.R
import com.listen.to.miskiatty.databinding.FragmentOrdersBinding
import com.listen.to.miskiatty.model.database.Order
import com.listen.to.miskiatty.viewmodel.OrderViewModel

class OrdersFragment : Fragment() {

    private var orderViewModel: OrderViewModel? = null
    private lateinit var binding: FragmentOrdersBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        setUpBinding(inflater, container)
        return  binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        callData()
        setRecyclerProductsAdapter()
        setUpListUpdate()
        setUpOnClickProduct()
        setUpRefresh()
    }

    private fun setUpBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil
                .inflate(
                        inflater,
                        R.layout.fragment_orders,
                        container,
                        false
                )

        orderViewModel = ViewModelProvider
                .NewInstanceFactory()
                .create(OrderViewModel::class.java)
        binding.orderViewModel = orderViewModel
    }

    override fun onCreateOptionsMenu(menu: Menu, infater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.menu_orders, menu)

        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu.findItem(R.id.search_order)
        val searchView = searchItem?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView.queryHint = "Buscar pedido"

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(order: String?): Boolean {
                if (order != null) orderViewModel?.searchOrder(order)
                return true
            }
        })
    }

    private fun setRecyclerProductsAdapter(){
        orderViewModel?.setRecyclerOrdersAdapter()
        orderViewModel?.setUpOrderDeleteSwiping(binding.rvOrders)
    }

    private fun setUpListUpdate(){
        orderViewModel?.getOrders()?.observe(viewLifecycleOwner, {
            orders: List<Order> ->
            Log.d("products", orders.toString())
            orderViewModel?.setOrdersInRecyclerAdapter(orders)
            binding.rvRefresh.isRefreshing = false
        })
    }

    private fun callData(){
        this.context?.let {
            orderViewModel?.callOrders(it, lifecycle)
            orderViewModel?.callProducts(it, lifecycle)
            orderViewModel?.callClients(it, lifecycle)
        }
    }

    private fun setUpRefresh(){
        binding.rvRefresh.apply {
            setColorSchemeResources(
                    R.color.colorPrimary,
                    R.color.colorAccent,
                    R.color.colorPrimaryDark)

            setOnRefreshListener {
                callData()
            }
        }
    }

    private fun setUpOnClickProduct(){
        orderViewModel?.orderClicked?.observe(viewLifecycleOwner, {
            startActivity(Intent(
                    this.activity?.applicationContext,
                    OrderDetailsActivity::class.java)
                    .apply {
                        putExtra("com.listen.to.miskiatty.view.ui.orders.ID", it.id)
                    })
        })
    }
}