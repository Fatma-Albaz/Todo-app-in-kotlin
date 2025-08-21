package com.route.todoapp.ui.splash

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.route.todoapp.databinding.ActivitySplashBinding
import com.route.todoapp.ui.task.MainActivity

class SplashActivity : Activity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val handler = Handler(mainLooper)
        val intent = Intent(this, MainActivity::class.java)
        handler.postDelayed({
            startActivity(intent)
            finish()
        }, 3000)
    }
}