package com.listen.to.miskiatty.view.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.listen.to.miskiatty.R
import com.listen.to.miskiatty.databinding.ActivitySetPinLoginBinding
import com.listen.to.miskiatty.model.provider.PreferenceProvider
import com.listen.to.miskiatty.view.ui.activities.MainActivity
import com.listen.to.miskiatty.viewmodel.SetPinLoginViewModel

class SetPinLoginActivity : AppCompatActivity() {

    private var setPinLoginViewModel: SetPinLoginViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_pin_login)
        setupBinding()

        setPinLoginViewModel?.validPin?.observe(this, Observer {
            if(it){
                PreferenceProvider(this).setPinLogin(setPinLoginViewModel?.PIN!!)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        })
    }

    private fun setupBinding(){
        val activitySetPinLoginActivity: ActivitySetPinLoginBinding = DataBindingUtil.setContentView(
                this,
                R.layout.activity_set_pin_login)

        setPinLoginViewModel =  ViewModelProvider
                .NewInstanceFactory()
                .create(SetPinLoginViewModel::class.java)
        activitySetPinLoginActivity.setPinLoginViewModel = setPinLoginViewModel
    }
}