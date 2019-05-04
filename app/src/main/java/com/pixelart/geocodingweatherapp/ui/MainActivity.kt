package com.pixelart.geocodingweatherapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.pixelart.geocodingweatherapp.R
import com.pixelart.geocodingweatherapp.common.PrefsManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var naveController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        naveController = Navigation.findNavController(this, R.id.nav_host_fragment)
        setUp(naveController)

        PrefsManager.INSTANCE.setContext(this.application)

        if (PrefsManager.INSTANCE.getTemperatureUnits() == null)
            PrefsManager.INSTANCE.setTemperatureUnits("metric")
    }

    private fun setUp(navController: NavController){
        nav_view.apply {
            NavigationUI.setupWithNavController(this, navController)
        }

        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(naveController, drawerLayout)
    }

    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START)
        else
            super.onBackPressed()
    }
}
