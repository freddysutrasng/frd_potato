package com.example.frd_potato

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.example.frd_potato.Onboarding.OnboardingActivity
import com.example.frd_potato.databinding.ActivitySplashScreenBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ikon status bar dibuat putih karena latar splash berwarna hijau gelap
        WindowCompat.getInsetsController(window, window.decorView)
            .isAppearanceLightStatusBars = false

        val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)
        val appPref = getSharedPreferences("app_pref", MODE_PRIVATE)

        lifecycleScope.launch {
            delay(2000) // simulasi pengambilan data selama 2 detik

            val sudahLihatPanduan = appPref.getBoolean("panduan_selesai", false)
            val isLogin = sharedPref.getBoolean("isLogin", false)

            val tujuan = when {
                !sudahLihatPanduan -> OnboardingActivity::class.java // pertama kali -> On Board Screen
                isLogin -> BaseActivity::class.java                  // sudah login -> Beranda
                else -> AuthActivity::class.java                     // belum login -> Login
            }

            startActivity(Intent(this@SplashScreenActivity, tujuan))
            finish() // Splash ditutup agar tidak bisa kembali ke sini
        }
    }
}