package com.listen.to.miskiatty.view.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.listen.to.miskiatty.R
import com.listen.to.miskiatty.databinding.ActivityPinLoginBinding
import com.listen.to.miskiatty.view.ui.activities.MainActivity
import com.listen.to.miskiatty.viewmodel.login.PinLoginViewModel

class PinLoginActivity : AppCompatActivity() {

    private var pinLoginViewModel: PinLoginViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin_login)
        setupBinding()

        pinLoginViewModel?.pinValid?.observe(this, {
            if (it == true) {
                startActivity(Intent(this, MainActivity::class.java).addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP))
                finish()
            }
        })
    }

    private fun setupBinding(){
        val activityPinLoginBinding: ActivityPinLoginBinding = DataBindingUtil.setContentView(
                this,
                R.layout.activity_pin_login)

        pinLoginViewModel =  ViewModelProvider
                .NewInstanceFactory()
                .create(PinLoginViewModel::class.java)
        activityPinLoginBinding.pinLoginViewModel = pinLoginViewModel
    }
}