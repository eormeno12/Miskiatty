package com.listen.to.miskiatty.view.ui.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.listen.to.miskiatty.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val sharedPreferences = getSharedPreferences("loginPref", Context.MODE_PRIVATE)
        val isLogged = sharedPreferences.getBoolean("logged", false)

        val activity = if (isLogged) PinLoginActivity::class.java else LoginActivity::class.java

        startActivity(Intent(this, activity))
        finish()
    }
}