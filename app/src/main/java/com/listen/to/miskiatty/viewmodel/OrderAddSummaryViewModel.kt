package com.listen.to.miskiatty.viewmodel

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.listen.to.miskiatty.R
import com.listen.to.miskiatty.model.adapters.AdapterCustomAddSummaryOrderProducts
import com.listen.to.miskiatty.model.adapters.AdapterCustomOrderProducts
import com.listen.to.miskiatty.model.database.Client
import com.listen.to.miskiatty.model.database.Order
import com.listen.to.miskiatty.model.database.Product
import com.listen.to.miskiatty.model.repository.orders.OrderAddSummaryRepository
import com.listen.to.miskiatty.model.repository.orders.OrderAddSummaryRepositoryImpl

class OrderAddSummaryViewModel: ViewModel() {
    private val orderAddSummaryRepository: OrderAddSummaryRepository =
        OrderAddSummaryRepositoryImpl()

    var orderOrderProductsAdapter: AdapterCustomAddSummaryOrderProducts? = null

    fun verifyIfIsAnUpdate(activity: AppCompatActivity): Boolean =
        orderAddSummaryRepository.verifyIfIsAnUpdate(activity)

    fun callClientById(context: Context, lifecycle: Lifecycle, id: Int) =
            orderAddSummaryRepository.callClientByIdRoom(context, lifecycle, id)

    fun getClientById(): LiveData<Client> =
            orderAddSummaryRepository.getClient()

    fun callOrder(activity: AppCompatActivity) =
        orderAddSummaryRepository.callOrderExtra(activity)

    fun getOrder(): LiveData<Order> =
        orderAddSummaryRepository.getOrder()

    fun getRecyclerOrdersProductsAdapter(): AdapterCustomAddSummaryOrderProducts? {
        return orderOrderProductsAdapter
    }

    fun setRecyclerOrdersAdapter() {
        orderOrderProductsAdapter = AdapterCustomAddSummaryOrderProducts(
            this,
            R.layout.template_order_products_add_summary
        )
    }

    fun setProductsInRecyclerAdapter(products: List<Product>) {
        if (orderOrderProductsAdapter != null) {
            orderOrderProductsAdapter?.setProductsList(products)
            orderOrderProductsAdapter?.notifyDataSetChanged()
        }
    }

    fun callProducts(appContext: Context, lifecycle: Lifecycle) =
            orderAddSummaryRepository.callProductsRoom(appContext, lifecycle)


    fun getProducts(): MutableLiveData<List<Product>> = orderAddSummaryRepository.getProducts()

    fun getProductAt(position: Int): Product? =
        orderOrderProductsAdapter?.getProductsList()?.get(position)

    fun addOrderRoom(context: Context, lifecycle: Lifecycle, order: Order) =
        orderAddSummaryRepository.insertOrderRoom(context, lifecycle, order)

    fun updateOrderRoom(context: Context, lifecycle: Lifecycle, order: Order) =
        orderAddSummaryRepository.updateOrderRoom(context, lifecycle, order)
}