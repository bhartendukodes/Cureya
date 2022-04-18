package com.example.cureyakotlin.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.cureyakotlin.R
import com.example.cureyakotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var navController:NavController?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setUpNavigation()
    }

    private fun setUpNavigation() {
        navController = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)?.findNavController()

        navController?.let {
            binding.bottomNav.setupWithNavController(it)
        }


        binding.bottomNav.setOnItemSelectedListener {
            if (navController?.currentDestination?.id==it.itemId)
            {
                return@setOnItemSelectedListener false
            }

            when(it.itemId)
            {
                R.id.userFragment -> {
                    navController?.navigate(R.id.userFragment)
                }
                R.id.usersFragment ->{
                    navController?.navigate(R.id.usersFragment)
                }
            }
            return@setOnItemSelectedListener true
        }
    }
}