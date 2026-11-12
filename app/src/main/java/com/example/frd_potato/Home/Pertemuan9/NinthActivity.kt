package com.example.frd_potato.Home.pertemuan_9

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.frd_potato.databinding.ActivityNinthBinding
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class NinthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNinthBinding
    private var tenor = "3 bulan"
    private var keperluan = "-"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNinthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar + tombol back
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Form Pengajuan Pinjaman"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        // 2. ChipGroup: deteksi chip tenor yang dipilih
        binding.chipGroupTenor.setOnCheckedStateChangeListener { group, checkedIds ->
            val selectedChipId = checkedIds.firstOrNull()   // ambil ID chip yang dipilih
            if (selectedChipId != null) {
                val chip = group.findViewById<Chip>(selectedChipId)
                tenor = chip.text.toString()
                Toast.makeText(this, "Tenor: $tenor", Toast.LENGTH_SHORT).show()
            }
        }

        // 3. GridLayout: 6 tombol keperluan
        val tombolKeperluan = listOf(
            binding.btnUsaha, binding.btnPendidikan, binding.btnKesehatan,
            binding.btnPupuk, binding.btnSembako, binding.btnDarurat
        )
        tombolKeperluan.forEach { tombol ->
            tombol.setOnClickListener {
                keperluan = tombol.text.toString()
                Toast.makeText(this, "Keperluan: $keperluan", Toast.LENGTH_SHORT).show()
            }
        }

        // 1 & 5. MaterialButton Ajukan + validasi TextInputLayout
        binding.btnAjukan.setOnClickListener {
            val teksNominal = binding.etNominal.text.toString()
            binding.textInputLayout.error = null

            if (teksNominal.isEmpty()) {
                binding.textInputLayout.error = "Nominal wajib diisi"
                return@setOnClickListener
            }
            if (teksNominal.toLong() < 500000) {
                binding.textInputLayout.error = "Nominal minimal Rp 500.000"
                return@setOnClickListener
            }

            val catatan = binding.etCatatan.text.toString().ifEmpty { "-" }
            MaterialAlertDialogBuilder(this)
                .setTitle("Ringkasan Pengajuan")
                .setMessage(
                    "Nominal: Rp $teksNominal\n" +
                            "Tenor: $tenor\n" +
                            "Keperluan: $keperluan\n" +
                            "Catatan: $catatan"
                )
                .setPositiveButton("OK", null)
                .show()
        }
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