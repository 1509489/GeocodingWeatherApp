package com.pixelart.geocodingweatherapp.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.test.espresso.idling.CountingIdlingResource
import com.pixelart.geocodingweatherapp.R
import com.pixelart.geocodingweatherapp.common.PrefsManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_add_location.*

class MainActivity : AppCompatActivity() {
    val countingIdlingResource = CountingIdlingResource("network_call")
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
        hideKeyboard()
        return NavigationUI.navigateUp(naveController, drawerLayout)
    }

    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START)
        else
            super.onBackPressed()
    }

    private fun hideKeyboard(){
        val inputmethodManager: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputmethodManager.hideSoftInputFromWindow(etLocation.windowToken, 0)
    }
}
