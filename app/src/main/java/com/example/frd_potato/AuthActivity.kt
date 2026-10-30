package com.example.frd_potato

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.frd_potato.databinding.ActivityAuthBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // "user_pref" = status login, "akun_pref" = akun hasil Registrasi
        val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)
        val akunPref = getSharedPreferences("akun_pref", MODE_PRIVATE)

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString()

            // Hapus pesan error lama
            binding.layoutUsername.error = null
            binding.layoutPassword.error = null

            // CEK 1: username dan password tidak boleh kosong
            if (username.isEmpty()) {
                binding.layoutUsername.error = "Username wajib diisi"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                binding.layoutPassword.error = "Password wajib diisi"
                return@setOnClickListener
            }

            // KONDISI 1: username sama dengan password
            val usernameSamaPassword = (username == password)

            // KONDISI 2: username & password sesuai data Registrasi di SharedPreferences
            val passwordTersimpan = akunPref.getString("password_$username", null)
            val akunTerdaftar = (passwordTersimpan != null && password == passwordTersimpan)

            if (usernameSamaPassword || akunTerdaftar) {
                // Login berhasil -> simpan status login
                val nama = akunPref.getString("nama_$username", username)

                val editor = sharedPref.edit()
                editor.putBoolean("isLogin", true)
                editor.putString("username", username)
                editor.putString("nama", nama)
                editor.apply()

                startActivity(Intent(this, BaseActivity::class.java))
                finish() // Login ditutup agar tombol back tidak kembali ke sini
            } else {
                // Login gagal -> MaterialAlertDialog
                MaterialAlertDialogBuilder(this)
                    .setTitle("Login Gagal")
                    .setMessage("Username atau password salah. Silahkan coba lagi.")
                    .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                    .show()
            }
        }

        // Tombol Register With Gmail -> halaman input email
        binding.btnRegisterGmail.setOnClickListener {
            startActivity(Intent(this, EmailInputActivity::class.java))
        }

        // Link "Daftar di sini" juga lewat halaman input email
        binding.tvDaftar.setOnClickListener {
            startActivity(Intent(this, EmailInputActivity::class.java))
        }
    }
}
