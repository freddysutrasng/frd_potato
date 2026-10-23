package com.example.frd_potato

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.frd_potato.Home.HomeFragment
import com.example.frd_potato.Pinjaman.PinjamanFragment
import com.example.frd_potato.Profil.ProfilFragment
import com.example.frd_potato.Riwayat.RiwayatFragment
import com.example.frd_potato.Simpanan.SimpananFragment
import com.example.frd_potato.databinding.ActivityBaseBinding

// BaseActivity = HALAMAN AWAL setelah login (pengganti MainActivity)
class BaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            // bottom = 0 supaya tidak ada jarak kosong di bawah Bottom Navigation
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        // Fragment default = Beranda
        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
        }

        binding.bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.simpanan -> {
                    replaceFragment(SimpananFragment())
                    true
                }
                R.id.pinjaman -> {
                    replaceFragment(PinjamanFragment())
                    true
                }
                R.id.riwayat -> {
                    replaceFragment(RiwayatFragment())
                    true
                }
                R.id.profil -> {
                    replaceFragment(ProfilFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            // addToBackStack dinonaktifkan agar tombol Back langsung keluar aplikasi
            .commit()
    }
}
