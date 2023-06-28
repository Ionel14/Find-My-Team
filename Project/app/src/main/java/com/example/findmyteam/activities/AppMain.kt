package com.example.findmyteam.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.findmyteam.R
import com.example.findmyteam.databinding.ActivityMainBinding
import com.example.findmyteam.databinding.AppMainContentBinding
import com.example.findmyteam.fragments.AccountFragment
import com.example.findmyteam.fragments.MainFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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