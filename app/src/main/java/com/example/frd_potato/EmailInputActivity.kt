package com.example.frd_potato

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.frd_potato.databinding.ActivityEmailInputBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class EmailInputActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmailInputBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmailInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar + tombol back
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Register With Gmail"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        binding.btnLanjut.setOnClickListener {
            val email = binding.etEmail.text.toString().trim().lowercase()

            // VALIDASI 1: email tidak boleh kosong
            if (email.isEmpty()) {
                tampilkanError("Email tidak boleh kosong.")
                return@setOnClickListener
            }

            // VALIDASI 2: email harus memakai domain @gmail.com
            // (dan harus ada nama sebelum @gmail.com, misalnya budi@gmail.com)
            if (!email.endsWith("@gmail.com") || email == "@gmail.com" || email.contains(" ")) {
                tampilkanError("Email harus menggunakan domain @gmail.com, contoh: budi@gmail.com")
                return@setOnClickListener
            }

            // VALID -> ke halaman Registrasi sambil membawa email
            val intent = Intent(this, RegisterActivity::class.java)
            intent.putExtra("email", email)
            startActivity(intent)
            finish() // halaman email ditutup, back dari Registrasi kembali ke Login
        }
    }

    // Menampilkan error dengan MaterialAlertDialog
    private fun tampilkanError(pesan: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Email Tidak Valid")
            .setMessage(pesan)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    // Agar tombol back di Toolbar berfungsi
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
