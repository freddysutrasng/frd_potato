package com.example.frd_potato

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.frd_potato.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar + tombol back
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Registrasi"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        // Ambil email yang dikirim dari halaman Register With Gmail
        val email = intent.getStringExtra("email") ?: ""
        binding.etEmail.setText(email)

        val akunPref = getSharedPreferences("akun_pref", MODE_PRIVATE)

        binding.btnDaftar.setOnClickListener {
            val nama = binding.etNama.text.toString().trim()
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            binding.layoutNama.error = null
            binding.layoutUsername.error = null
            binding.layoutPassword.error = null

            // VALIDASI 1: semua field wajib diisi
            if (nama.isEmpty()) {
                binding.layoutNama.error = "Nama wajib diisi"
                return@setOnClickListener
            }
            if (email.isEmpty()) {
                binding.layoutEmail.error = "Email kosong, ulangi dari Register With Gmail"
                return@setOnClickListener
            }
            if (username.isEmpty()) {
                binding.layoutUsername.error = "Username wajib diisi"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                binding.layoutPassword.error = "Password wajib diisi"
                return@setOnClickListener
            }

            // VALIDASI 2: username tidak boleh mengandung spasi
            if (username.contains(" ")) {
                binding.layoutUsername.error = "Username tidak boleh mengandung spasi"
                return@setOnClickListener
            }
            if (akunPref.contains("password_$username")) {
                binding.layoutUsername.error = "Username sudah dipakai"
                return@setOnClickListener
            }

            // VALIDASI 3: password minimal 6 karakter
            if (password.length < 6) {
                binding.layoutPassword.error = "Password minimal 6 karakter"
                return@setOnClickListener
            }

            // Lolos validasi -> simpan seluruh data registrasi ke SharedPreferences
            val editor = akunPref.edit()
            editor.putString("nama_$username", nama)
            editor.putString("email_$username", email)
            editor.putString("password_$username", password)
            editor.apply()

            // Tampilkan halaman informasi "Registrasi Berhasil"
            val intentBerhasil = Intent(this, RegisterSuccessActivity::class.java)
            intentBerhasil.putExtra("nama", nama)
            intentBerhasil.putExtra("email", email)
            intentBerhasil.putExtra("username", username)
            startActivity(intentBerhasil)
            finish() // halaman Registrasi ditutup
        }
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
