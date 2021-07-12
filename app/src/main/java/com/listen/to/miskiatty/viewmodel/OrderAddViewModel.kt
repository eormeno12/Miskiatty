package com.listen.to.miskiatty.viewmodel

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.listen.to.miskiatty.R
import com.listen.to.miskiatty.model.adapters.AdapterCustomAddOrderProducts
import com.listen.to.miskiatty.model.database.Client
import com.listen.to.miskiatty.model.database.Order
import com.listen.to.miskiatty.model.database.Product
import com.listen.to.miskiatty.model.messages.ErrorsEnum
import com.listen.to.miskiatty.model.repository.orders.OrderAddRepository
import com.listen.to.miskiatty.model.repository.orders.OrderAddRepositoryImpl
import com.listen.to.miskiatty.view.ui.orders.OrderAddSummaryActivity
import com.listen.to.wave.view.message.ToastFactory
import java.lang.Exception
import java.lang.NullPointerException
import java.util.*
import kotlin.collections.ArrayList

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

    fun callOrder(activity: AppCompatActivity) =
        orderAddRepository.callOrderExtra(activity)

    fun getOrder(): LiveData<Order> =
        orderAddRepository.getOrder()

    fun getProducts(): LiveData<List<Product>> = orderAddRepository.getProducts()

    fun callProducts(appContext: Context, lifecycle: Lifecycle) =
            orderAddRepository.callProductsROOM(appContext, lifecycle)

    private fun callProductsById(appContext: Context, lifecycle: Lifecycle, id:List<Int>) =
            orderAddRepository.callProductsByIdROOM(appContext, lifecycle, id)

    private fun getProductsById(): LiveData<List<Product>> = orderAddRepository.getProductsById()

    fun getClients(): MutableLiveData<List<Client>> = orderAddRepository.getClients()

    fun callClients(appContext: Context, lifecycle: Lifecycle) =
            orderAddRepository.callClientsROOM(appContext, lifecycle)

    fun getClientById(): MutableLiveData<Client> =
            orderAddRepository.getClientById()

    fun callClientById(context: Context, lifecycle: Lifecycle, id:Int) =
            orderAddRepository.callClientById(context, lifecycle, id)

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
        val product = getProductAt(position)

        if (checkedProductList.contains(product)){
            checkedProductQuantityList.removeAt(checkedProductList.indexOf(product))
            checkedProductList.remove(product)
        }
        else
            product?.let {
                checkedProductList.add(it)
                checkedProductQuantityList.add(productQuantity.toInt())

                tv_productQuantity.addTextChangedListener {
                    onQuantityChange(position, tv_productQuantity)
                }
            }
    }

    fun setCheckedProducts(activity: AppCompatActivity, lifecycle: Lifecycle, order: Order){
        val context = activity.applicationContext
        callProductsById(context, lifecycle, order.products)

        val productQuantities = order.productsQuantity

        getProductsById().observe(activity, { products ->
            val productsChecked: HashMap<Int, Int> = HashMap()

            for(idx in 0 until products.count()){
                val product = products[idx]
                if (checkedProductList.contains(product)){
                    checkedProductQuantityList.removeAt(checkedProductList.indexOf(product))
                    checkedProductList.remove(product)
                }
                else
                    product.let {
                        checkedProductList.add(it)
                        checkedProductQuantityList.add(productQuantities[idx])

                        addOrderProductsAdapter?.getProductsList()?.indexOf(it)?.let { position ->
                            productsChecked[position] = productQuantities[idx]
                        }
                    }
            }

            addOrderProductsAdapter?.setProductsCheckedMap(productsChecked)
        })
    }

    fun isProductChecked(position: Int): Boolean? =
            addOrderProductsAdapter?.getProductsCheckedMap()?.keys?.contains(position)

    fun getProductQuantity(position: Int): Int? =
            try {
                addOrderProductsAdapter?.getProductsCheckedMap()?.get(position)
            }catch (e: Exception){
                1
            }

    private fun onQuantityChange(position: Int, tv_productQuantity: EditText){
        val productQuantity = tv_productQuantity.text.toString()
        val product = getProductAt(position)

        if(checkedProductList.contains(product)  && tv_productQuantity.text.isNotEmpty()){
            val idx = checkedProductList.indexOf(product)
            checkedProductQuantityList[idx] = productQuantity.toInt()
        }
    }

    fun getProductAt(position: Int): Product? =
        addOrderProductsAdapter?.getProductsList()?.get(position)

    fun onClickNextToSummary(context: Context){
        try {
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

            getOrder().value?.id?.let {
                order.id = it
            }

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
        }catch (e:NullPointerException){
            ToastFactory().displayErrorToast(context, ErrorsEnum.EMPTY_TEXT_FIELD)
        }
    }

    fun onClickEtDateTime(editText: TextInputEditText, context: Context){
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)

        val hour = calendar.get(Calendar.HOUR)
        val minute = calendar.get(Calendar.MINUTE)


        val datePickerDialog = DatePickerDialog(context, { datePicker, y, m, d ->
            editText.setText("$d/$m/$y")
        }, year, month, day)

        val timePickerDialog = TimePickerDialog(context, { timePicker, h, m ->
            editText.setText("${editText.text} $h:$m")
        }, hour, minute, true)

        timePickerDialog.show()
        datePickerDialog.show()
    }
}