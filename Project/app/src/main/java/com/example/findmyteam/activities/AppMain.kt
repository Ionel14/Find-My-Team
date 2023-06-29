package com.example.findmyteam.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.findmyteam.R
import com.example.findmyteam.databinding.AppMainContentBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class AppMain : AppCompatActivity() {

    private lateinit var binding: AppMainContentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AppMainContentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val thread = Thread()
        kotlin.concurrent.thread {

            val bottomNavigation = findViewById<BottomNavigationView>(R.id.menu_bar_app)
            val navController = findNavController(R.id.nav_host_fragment_main_app)

            bottomNavigation.setupWithNavController(navController)
        }
        thread.start()

    }
}