package com.listen.to.miskiatty.view.ui.products

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.listen.to.miskiatty.R
import com.listen.to.miskiatty.databinding.ActivityProductAddBinding
import com.listen.to.miskiatty.model.database.Product
import com.listen.to.miskiatty.model.network.storage.FireStorageService
import com.listen.to.miskiatty.viewmodel.CallbackFireStorage
import com.listen.to.miskiatty.viewmodel.ProductAddViewModel
import com.squareup.picasso.Picasso
import java.lang.Exception

class ProductAddActivity : AppCompatActivity() {

    private var productAddViewModel: ProductAddViewModel? = null
    private var navigationComesFromProductDetails: Boolean = false
    private lateinit var binding: ActivityProductAddBinding
    private lateinit var toolbar: Toolbar
    private var productId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_add)

        setupBinding()
        setUpToolbar()

        navigationComesFromProductDetails =
                productAddViewModel?.intentHasData(this) == true

        if (navigationComesFromProductDetails){
            productAddViewModel?.callProduct(this, lifecycle)

            productAddViewModel?.getProduct()?.observe(this, { product ->
                productId = product.id
                binding.product.editText?.setText(product.name)
                binding.price.editText?.setText(product.price.toString())
                binding.cost.editText?.setText(product.cost.toString())
                binding.recipe.editText?.setText(product.recipe)

                imageUri = product.image
                Picasso.get().load(imageUri).resize(0, 200).into( binding.btImage)
                binding.btImage.scaleType = ImageView.ScaleType.CENTER_CROP
            })
        }
    }

    private fun setupBinding(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_add)
        productAddViewModel = ViewModelProvider
                .NewInstanceFactory()
                .create(ProductAddViewModel::class.java)

        binding.productAddViewModel = productAddViewModel
    }

    var imageUri: String? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == productAddViewModel?.REQUEST_IMAGE_GALLERY){
            if(resultCode == Activity.RESULT_OK && data != null){
                imageUri = data.data!!.toString()
                Picasso.get().load(imageUri).resize(0, 200).into( binding.btImage)
                binding.btImage.scaleType = ImageView.ScaleType.CENTER_CROP
            }
        }
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

    var imageUrl: String? = null

    private fun toolbarItemOnClickListener(){
        toolbar.setOnMenuItemClickListener { menu ->
            when(menu.itemId){
                R.id.confirm_product -> {
                    FireStorageService().uploadFileFromUri(Uri.parse(imageUri), object: CallbackFireStorage<String>{
                        override fun onSucces(result: String?) {
                            if(result != null){
                                imageUrl = result

                                if (navigationComesFromProductDetails)
                                    productAddViewModel?.updateProductRoom(applicationContext,
                                        lifecycle,
                                        buildProductForUpdate())
                                else
                                    productAddViewModel?.addProductRoom(applicationContext,
                                        lifecycle,
                                        buildProduct())

                                onBackPressed()
                                binding.progressCircular.visibility = View.INVISIBLE
                                finish()
                            }
                        }

                        override fun onProgress(progress: Double) {
                            binding.progressCircular.visibility = View.VISIBLE
                        }

                        override fun onFailure(e: Exception) {
                            binding.progressCircular.visibility = View.INVISIBLE
                        }

                    })
                    true
                }

                else -> false
            }
        }
    }


    private fun buildProduct(): Product{
        return Product(
                image = imageUrl!!,
                name = binding.product.editText?.text.toString(),
                price = binding.price.editText?.text.toString().toFloat(),
                cost = binding.cost.editText?.text.toString().toFloat(),
                recipe = binding.recipe.editText?.text.toString()
        )
    }

    private fun buildProductForUpdate(): Product{
        return Product(
                id = productId!!,
                image = imageUrl!!,
                name = binding.product.editText?.text.toString(),
                price = binding.price.editText?.text.toString().toFloat(),
                cost = binding.cost.editText?.text.toString().toFloat(),
                recipe = binding.recipe.editText?.text.toString()
        )
    }
}