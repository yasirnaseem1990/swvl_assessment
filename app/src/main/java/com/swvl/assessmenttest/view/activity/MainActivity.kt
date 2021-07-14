package com.swvl.assessmenttest.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.swvl.assessmenttest.R

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var navHostFragment: NavHostFragment

    private lateinit var navController: NavController

    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        navController = navHostFragment.navController


        toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)

        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp() =
        Navigation.findNavController(this, R.id.fragment).navigateUp()
}