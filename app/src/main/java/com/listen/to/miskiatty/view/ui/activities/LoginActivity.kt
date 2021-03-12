package com.listen.to.miskiatty.view.ui.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.listen.to.miskiatty.R
import com.listen.to.miskiatty.databinding.ActivityLoginBinding
import com.listen.to.miskiatty.model.utils.edit
import com.listen.to.miskiatty.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private var loginViewModel: LoginViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setupBinding()

        val sharedPreferences = getSharedPreferences("loginPref", Context.MODE_PRIVATE)

        loginViewModel?.successLogin?.observe(this, Observer {
            if (it) {
                sharedPreferences.edit{
                    putBoolean("logged", true)
                }

                startActivity(Intent(this, MainActivity::class.java))
            }
        })
    }

    fun setupBinding(){
        val activityLoginBinding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        activityLoginBinding.loginViewModel = loginViewModel
    }
}