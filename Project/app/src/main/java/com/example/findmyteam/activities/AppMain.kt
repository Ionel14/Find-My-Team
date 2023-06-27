package com.example.findmyteam.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import com.example.findmyteam.R
import com.example.findmyteam.databinding.ActivityMainBinding
import com.example.findmyteam.databinding.AppMainContentBinding

class AppMain : AppCompatActivity() {

    private lateinit var binding: AppMainContentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AppMainContentBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}