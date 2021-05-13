package com.listen.to.miskiatty.view.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.listen.to.miskiatty.R
import com.listen.to.miskiatty.model.provider.PreferenceProvider
import com.listen.to.miskiatty.view.ui.login.LoginActivity
import com.listen.to.miskiatty.view.ui.login.PinLoginActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        PreferenceProvider(this).setEmailLogin("prueba@gmail.com")
        PreferenceProvider(this).setPasswordLogin("MiskiattyAdmin123#")

        val isLogged = PreferenceProvider(this).isLogged()

        val activity = if (isLogged) PinLoginActivity::class.java else LoginActivity::class.java

        startActivity(Intent(this, activity))
        finish()
    }
}