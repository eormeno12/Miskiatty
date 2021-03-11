package com.listen.to.miskiatty.view.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.listen.to.miskiatty.R
import com.listen.to.miskiatty.databinding.ActivityLoginBinding
import com.listen.to.miskiatty.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private var loginViewModel: LoginViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setupBinding(savedInstanceState)

        loginViewModel?.successLogin?.observe(this, Observer {
            if (it) startActivity(Intent(this, MainActivity::class.java))
        })
    }

    fun setupBinding(savedInstanceState: Bundle?){
        val activityLoginBinding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        activityLoginBinding.loginViewModel = loginViewModel
    }
}