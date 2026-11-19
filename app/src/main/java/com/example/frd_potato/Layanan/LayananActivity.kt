package com.example.frd_potato.Layanan

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.frd_potato.R
import com.example.frd_potato.databinding.ActivityLayananBinding
import com.google.android.material.tabs.TabLayoutMediator

class LayananActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLayananBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLayananBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar + tombol back
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Info & Layanan Koperasi"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        // 1. Inisialisasi adapter
        val tabAdapter = LayananTabAdapter(this)

        // 2. Pasang adapter ke ViewPager2
        binding.vpLayanan.adapter = tabAdapter

        // 3. Hubungkan TabLayout dan ViewPager2
        TabLayoutMediator(binding.tabLayanan, binding.vpLayanan) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Notifikasi"
                    tab.icon = ContextCompat.getDrawable(this, R.drawable.ic_notifikasi)
                    // Badge tanpa angka (hanya titik)
                    val badge = tab.getOrCreateBadge()
                    badge.isVisible = true
                }
                1 -> {
                    tab.text = "Jadwal"
                    tab.icon = ContextCompat.getDrawable(this, R.drawable.ic_angsuran)
                    // Badge dengan angka (jumlah cicilan)
                    val badge = tab.getOrCreateBadge()
                    badge.isVisible = true
                    badge.number = 3
                }
                2 -> {
                    tab.text = "Layanan"
                    tab.icon = ContextCompat.getDrawable(this, R.drawable.ic_layanan)
                }
            }
        }.attach()

        // Buka tab tertentu kalau dikirim dari Beranda (0 = Notifikasi, 2 = Layanan)
        val tabAwal = intent.getIntExtra("tab", 0)
        binding.vpLayanan.setCurrentItem(tabAwal, false)
    }

    // Tombol back di Toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}