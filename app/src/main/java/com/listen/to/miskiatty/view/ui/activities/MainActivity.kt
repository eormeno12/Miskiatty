package com.listen.to.miskiatty.view.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.listen.to.miskiatty.R
import com.listen.to.miskiatty.databinding.ActivityMainBinding
import com.listen.to.miskiatty.model.database.room.FirebaseBackup


class MainActivity : AppCompatActivity() {

    lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController =  Navigation.findNavController(this, R.id.fr_navHost)

        setUpActionBar()
        configBottomNav()
        configUpNav()
        configDrawerLayout()
    }

    override fun onPause() {
        super.onPause()
        val firebaseBackup = FirebaseBackup(this, lifecycle)
        firebaseBackup.uploadBackup()
    }

    private fun setUpActionBar(){
        setSupportActionBar(binding.toolbarMain)
    }

    private fun configBottomNav(){
        navController =  Navigation.findNavController(
                this,
                R.id.fr_navHost)

        NavigationUI.setupWithNavController(binding.bnvMain, navController)
    }

    private fun configUpNav(){
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.statisticsFragment, R.id.clientsFragment,
                R.id.productsFragment, R.id.ordersFragment), binding.drawerLayoutMain)

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }

    private fun configDrawerLayout(){
        NavigationUI.setupWithNavController(binding.navViewMain, navController)
    }
}