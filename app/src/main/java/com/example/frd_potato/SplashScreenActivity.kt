package com.example.frd_potato

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.frd_potato.databinding.ActivitySplashScreenBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)

        lifecycleScope.launch {
            delay(2000) // simulasi pengambilan data 2 detik

            val isLogin = sharedPref.getBoolean("isLogin", false)

            val target = if (isLogin) BaseActivity::class.java   // <-- diganti dari MainActivity
            else AuthActivity::class.java

            startActivity(Intent(this@SplashScreenActivity, target))
            finish()
        }
    }
}