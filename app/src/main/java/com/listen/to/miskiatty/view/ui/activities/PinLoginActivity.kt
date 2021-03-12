package com.listen.to.miskiatty.view.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.listen.to.miskiatty.R
import com.listen.to.miskiatty.databinding.ActivityPinLoginBinding
import com.listen.to.miskiatty.viewmodel.PinLoginViewModel

class PinLoginActivity : AppCompatActivity() {

    private var pinLoginActivity: PinLoginViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin_login)
        setupBinding()

        pinLoginActivity?.successPinLogin?.observe(this, Observer {
            if (it == true) startActivity(Intent(this, MainActivity::class.java))
        })
    }

    fun setupBinding(){
        val activityPinLoginBinding: ActivityPinLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_pin_login)
        pinLoginActivity = ViewModelProvider(this).get(PinLoginViewModel::class.java)
        activityPinLoginBinding.pinLoginViewModel = pinLoginActivity
    }
}