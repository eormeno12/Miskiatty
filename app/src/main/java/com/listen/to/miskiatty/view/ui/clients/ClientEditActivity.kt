package com.listen.to.miskiatty.view.ui.clients

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.listen.to.miskiatty.R
import com.listen.to.miskiatty.databinding.ActivityClientEditBinding
import com.listen.to.miskiatty.model.database.Client
import com.listen.to.miskiatty.model.network.storage.FireStorageService
import com.listen.to.miskiatty.view.ui.activities.MainActivity
import com.listen.to.miskiatty.viewmodel.CallbackFireStorage
import com.listen.to.miskiatty.viewmodel.ClientEditViewModel
import com.squareup.picasso.Picasso
import java.lang.Exception

class ClientEditActivity : AppCompatActivity() {

    private var clientEditViewModel: ClientEditViewModel? = null
    private lateinit var binding: ActivityClientEditBinding
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpBinding()
        setContentView(binding.root)

        setUpToolbar()
        callClients()
        setUpClientUpdate()
    }

    override fun onStart() {
        super.onStart()
        binding.lifecycleOwner = this
    }

    private fun setUpBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_client_edit)

        clientEditViewModel = ViewModelProvider
                .NewInstanceFactory()
                .create(ClientEditViewModel::class.java)

        binding.clientEditViewModel = clientEditViewModel
    }

    private fun callClients() {
        with(clientEditViewModel){
            this?.let {
                callClient(this@ClientEditActivity, lifecycle)
            }
        }
    }

    private fun setUpClientUpdate(){
        with(clientEditViewModel){
            this?.let {
                getClient().observe(this@ClientEditActivity, { client ->
                    name = client.name
                    phone = client.phone
                    address = client.address

                    binding.name.editText?.setText(name)
                    binding.phone.editText?.setText(phone)
                    binding.address.editText?.setText(address)
                })
            }
        }

    }

    private var imageUri: String? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == clientEditViewModel?.REQUEST_IMAGE_GALLERY){
            if(resultCode == Activity.RESULT_OK && data != null){

                imageUri = data.data!!.toString()
                Picasso.get().load(imageUri).resize(0, 200).into( binding.btImage)
                binding.btImage.scaleType = ImageView.ScaleType.CENTER_CROP
            }
        }
    }

    private fun setUpToolbar(){
        toolbar = binding.toolbarClientEdit
        toolbarNavigationOnClickListener()
        toolbarItemOnClickListener()
    }

    private fun toolbarItemOnClickListener(){
        toolbar.setOnMenuItemClickListener { menu ->
            when(menu.itemId){
                R.id.confirm_client_edit -> {

                    with(clientEditViewModel){
                        this?.let {
                            imageUri = if(imageUri != null) imageUri else getClient().value?.image!!

                            FireStorageService().uploadFileFromUri(Uri.parse(imageUri),
                                object: CallbackFireStorage<String> {
                                    override fun onSucces(result: String?) {
                                        if(result != null){
                                            val client = Client(
                                                id = getClient().value?.id!!,
                                                image = result,
                                                name = name,
                                                phone = phone,
                                                address = address,
                                                orders = getClient().value?.orders!!)

                                            client.let { clientEditViewModel?.updateClientRoom(this@ClientEditActivity, lifecycle, it)}
                                            startActivity(Intent(this@ClientEditActivity, MainActivity::class.java))
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

                                }
                            )
                        }
                    }
                    true
                }

                else -> false
            }
        }
    }


    private fun toolbarNavigationOnClickListener(){
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}