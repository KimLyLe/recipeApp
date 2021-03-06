package com.example.recipeapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.recipeapp.R

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        initNavigation()
    }


    private fun initNavigation() {
        // The NavController

        val navController = findNavController(R.id.navHostFragment)

        // Connect the navHostFragment with the BottomNavigationView.
        NavigationUI.setupWithNavController(navView, navController)

        // Connect the navHostFragment with the Toolbar.
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.homeFragment, R.id.toBuyFragment))
        toolbar.setupWithNavController(navController, appBarConfiguration)

        // Add a Destination Changed Listener. This gets called whenever the navigation controller is navigating to another fragment.
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.homeFragment -> navView.visibility = View.VISIBLE
                R.id.toBuyFragment -> navView.visibility = View.VISIBLE
            }
        }
    }

}
