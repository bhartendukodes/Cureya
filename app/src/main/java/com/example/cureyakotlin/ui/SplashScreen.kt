package com.example.cureyakotlin.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.cureyakotlin.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        lifecycleScope.launch {
            delay(4000)
            var intent = Intent(this@SplashScreen, LoginActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }
}
