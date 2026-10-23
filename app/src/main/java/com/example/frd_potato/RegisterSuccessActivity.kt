package com.example.frd_potato

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.frd_potato.databinding.ActivityRegisterSuccessBinding

class RegisterSuccessActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterSuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar + tombol back
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Registrasi Berhasil"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        // Tampilkan data yang baru didaftarkan
        binding.tvNama.text = intent.getStringExtra("nama") ?: "-"
        binding.tvEmail.text = intent.getStringExtra("email") ?: "-"
        binding.tvUsername.text = intent.getStringExtra("username") ?: "-"

        // Kembali ke halaman Login
        binding.btnMasuk.setOnClickListener {
            finish()
        }
    }

    // Tombol back di Toolbar juga kembali ke Login
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
