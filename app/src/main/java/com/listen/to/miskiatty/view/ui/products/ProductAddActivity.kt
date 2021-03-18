package com.listen.to.miskiatty.view.ui.products

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.listen.to.miskiatty.R
import com.listen.to.miskiatty.databinding.ActivityProductAddBinding
import com.listen.to.miskiatty.generated.callback.OnClickListener
import com.listen.to.miskiatty.model.database.Product
import com.listen.to.miskiatty.viewmodel.LoginViewModel
import com.listen.to.miskiatty.viewmodel.ProductAddViewModel
import com.listen.to.miskiatty.viewmodel.ProductViewModel

class ProductAddActivity : AppCompatActivity() {

    private var productViewModel: ProductAddViewModel? = null
    private lateinit var binding: ActivityProductAddBinding
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpProductViewModel()
        setUpToolbar()
    }

    private fun setUpProductViewModel() {
        productViewModel = ViewModelProvider
                .NewInstanceFactory()
                .create(ProductAddViewModel::class.java)
    }

    private fun setUpToolbar(){
        toolbar = binding.toolbarProductAdd
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
                R.id.confirm_product -> {
                    productViewModel?.addProductRoom(applicationContext,
                            lifecycle,
                            buildProduct())

                    onBackPressed()
                    true
                }

                else -> false
            }
        }
    }

    private fun buildProduct(): Product{
        return Product(
                image = 10,
                name = binding.product.editText?.text.toString(),
                price = binding.price.editText?.text.toString().toFloat(),
                cost = binding.cost.editText?.text.toString().toFloat(),
                recipe = binding.recipe.editText?.text.toString()
        )
    }
}