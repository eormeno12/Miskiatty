package com.listen.to.miskiatty.view.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.listen.to.miskiatty.R
import com.listen.to.miskiatty.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configNav()
    }

    fun configNav(){
        NavigationUI.setupWithNavController(
                binding.bnvMain,
                Navigation.findNavController(
                        this,
                        R.id.fr_forAllFragments_main))
    }
}