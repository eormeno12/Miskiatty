package com.listen.to.miskiatty.viewmodel

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.listen.to.miskiatty.R
import com.listen.to.miskiatty.model.adapters.AdapterCustomAddOrderProducts
import com.listen.to.miskiatty.model.database.Client
import com.listen.to.miskiatty.model.database.Order
import com.listen.to.miskiatty.model.database.Product
import com.listen.to.miskiatty.model.repository.orders.OrderAddRepository
import com.listen.to.miskiatty.model.repository.orders.OrderAddRepositoryImpl
import com.listen.to.miskiatty.view.ui.orders.OrderAddSummaryActivity

class OrderAddViewModel: ViewModel() {
    private val orderAddRepository: OrderAddRepository = OrderAddRepositoryImpl()

    var addOrderProductsAdapter: AdapterCustomAddOrderProducts? = null

    private var checkedProductList = ArrayList<Product>()
    private var checkedProductQuantityList = ArrayList<Int>()

    var client: Client? = null
    var address = ""
    var deliveryDate = ""
    var state = ""

    fun intentHasData(activity: AppCompatActivity) =
        orderAddRepository.verifyIntentData(activity)

    fun getClientById(id: Int): Client? {
        val clients = getClients().value
        clients?.let {
            for (client in it)
                if (client.id == id) return client
        }

        return null
    }

    fun callOrder(activity: AppCompatActivity) =
        orderAddRepository.callOrderExtra(activity)

    fun getOrder(): LiveData<Order> =
        orderAddRepository.getOrder()

    fun getProducts(): LiveData<List<Product>> = orderAddRepository.getProducts()

    fun callProducts(appContext: Context, lifecycle: Lifecycle) {
        orderAddRepository.callProductsROOM(appContext, lifecycle)
    }

    fun getClients(): MutableLiveData<List<Client>> = orderAddRepository.getClients()

    fun callClients(appContext: Context, lifecycle: Lifecycle) {
        orderAddRepository.callClientsROOM(appContext, lifecycle)
    }

    fun getRecyclerOrdersProductsAdapter(): AdapterCustomAddOrderProducts?{
        return addOrderProductsAdapter
    }

    fun setRecyclerOrdersAdapter(){
        addOrderProductsAdapter = AdapterCustomAddOrderProducts(
            this,
            R.layout.template_order_products_add)
    }

    fun setProductsInRecyclerAdapter(products: List<Product>) {
        if (addOrderProductsAdapter != null) {
            addOrderProductsAdapter?.setProductsList(products)
            addOrderProductsAdapter?.notifyDataSetChanged()
        }
    }

    fun onClickListenerCheckBox(position: Int, tv_productQuantity: EditText){
        val productQuantity = tv_productQuantity.text.toString()
        val contact = getProductAt(position)

        if (checkedProductList.contains(contact)){
            checkedProductQuantityList.remove(checkedProductList.indexOf(contact))
            checkedProductList.remove(contact)
        }
        else
            contact?.let {
                checkedProductList.add(it)
                checkedProductQuantityList.add(productQuantity.toInt())}
    }

    fun getProductAt(position: Int): Product? =
        addOrderProductsAdapter?.getProductsList()?.get(position)

    fun onClickNextToSummary(context: Context){
        var totalPrice = 0f

        for(i in 0 until checkedProductQuantityList.size)
            totalPrice += (checkedProductList[i].price * checkedProductQuantityList[i])

        val checkedProductsIdList = ArrayList<Int>()
        for (product in checkedProductList)
            checkedProductsIdList.add(product.id)

        val order = Order(
            client = client?.id!!,
            address = address,
            deliveryDate = deliveryDate,
            state = state,
            totalPrice = totalPrice,
            products = checkedProductsIdList,
            productsQuantity = checkedProductQuantityList
        )

        with(context){
            startActivity(Intent(
                applicationContext,
                OrderAddSummaryActivity::class.java)
                .apply {
                    putExtra("com.listen.to.miskiatty.view.ui.orders.DETAILS", order)
                    putExtra("com.listen.to.miskiatty.view.ui.orders.UPDATE", intentHasData(context as AppCompatActivity))
                }
            )
        }
    }
}