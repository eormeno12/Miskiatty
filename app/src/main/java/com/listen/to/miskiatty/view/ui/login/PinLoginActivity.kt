package com.listen.to.miskiatty.view.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.listen.to.miskiatty.R
import com.listen.to.miskiatty.databinding.ActivityPinLoginBinding
import com.listen.to.miskiatty.view.ui.activities.MainActivity
import com.listen.to.miskiatty.viewmodel.PinLoginViewModel

class PinLoginActivity : AppCompatActivity() {

    private var pinLoginViewModel: PinLoginViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin_login)
        setupBinding()

        pinLoginViewModel?.pinValid?.observe(this, Observer {
            if (it == true) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        })
    }

    private fun setupBinding(){
        val activityPinLoginBinding: ActivityPinLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_pin_login)
        pinLoginViewModel = ViewModelProvider(this).get(PinLoginViewModel::class.java)
        activityPinLoginBinding.pinLoginViewModel = pinLoginViewModel
    }
}