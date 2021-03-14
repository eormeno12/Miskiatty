package com.listen.to.miskiatty.view.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.listen.to.miskiatty.R
import com.listen.to.miskiatty.databinding.ActivityLoginBinding
import com.listen.to.miskiatty.model.provider.PreferenceProvider
import com.listen.to.miskiatty.view.ui.activities.MainActivity
import com.listen.to.miskiatty.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private var loginViewModel: LoginViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setupBinding()

        val preferenceProvider = PreferenceProvider(this)

        loginViewModel?.successLogin?.observe(this, Observer {
            if (it) {
                preferenceProvider.saveLogin()

                if(preferenceProvider.isPinLoginSaved()){
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }else {
                    startActivity(Intent(this, SetPinLoginActivity::class.java))
                }
            }
        })
    }

    private fun setupBinding(){
        val activityLoginBinding: ActivityLoginBinding = DataBindingUtil.setContentView(
                this,
                R.layout.activity_login)

        loginViewModel = ViewModelProvider
                .NewInstanceFactory()
                .create(LoginViewModel::class.java)
        activityLoginBinding.loginViewModel = loginViewModel
    }
}