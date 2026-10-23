package com.example.frd_potato

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.frd_potato.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // "user_pref" = status login, "akun_pref" = akun hasil Daftar Anggota
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

            // CEK 2: apakah akun demo?
            val akunDemo = (username == "anggota" && password == "123456")

            // CEK 3: apakah akun hasil Daftar Anggota, dan password-nya sama?
            val passwordTersimpan = akunPref.getString("password_$username", null)
            val akunTerdaftar = (passwordTersimpan != null && password == passwordTersimpan)

            if (akunDemo || akunTerdaftar) {
                // Login berhasil -> simpan status login
                val nama = if (akunDemo) {
                    "Anggota Demo"
                } else {
                    akunPref.getString("nama_$username", username)
                }

                val editor = sharedPref.edit()
                editor.putBoolean("isLogin", true)
                editor.putString("username", username)
                editor.putString("nama", nama)
                editor.apply()

                startActivity(Intent(this, BaseActivity::class.java))
                finish() // Login ditutup agar tombol back tidak kembali ke sini
            } else {
                // Login gagal
                AlertDialog.Builder(this)
                    .setTitle("Login Gagal")
                    .setMessage("Username atau password salah. Silahkan coba lagi.")
                    .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                    .show()
            }
        }

        // Pindah ke halaman Daftar Anggota
        binding.tvDaftar.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
