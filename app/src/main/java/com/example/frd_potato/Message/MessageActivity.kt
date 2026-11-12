package com.example.frd_potato.Message

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.frd_potato.databinding.ActivityMessageBinding

class MessageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMessageBinding

    // Data contoh pesan dari pengurus koperasi
    private val messageList = listOf(
        MessageModel("Pak Darman · Ketua", "Rapat anggota tahunan hari Sabtu jam 09.00 di balai Afdeling 2.", "https://avatar.iran.liara.run/public/1"),
        MessageModel("Bu Sari · Bendahara", "Setoran simpanan wajib November paling lambat tanggal 25.", "https://avatar.iran.liara.run/public/61"),
        MessageModel("Pak Rudi · Sekretaris", "Formulir pinjaman baru sudah bisa diambil di kantor koperasi.", "https://avatar.iran.liara.run/public/12"),
        MessageModel("Bu Lestari · Petugas Simpanan", "Simpanan sukarela bisa ditarik setiap hari kerja jam 08.00–15.00.", "https://avatar.iran.liara.run/public/65"),
        MessageModel("Pak Hendra · Petugas Pinjaman", "Pengajuan pinjaman Anda sedang diperiksa pengurus.", "https://avatar.iran.liara.run/public/20"),
        MessageModel("Bu Wati · Pengawas", "Laporan keuangan bulan Oktober sudah ditempel di papan informasi.", "https://avatar.iran.liara.run/public/70"),
        MessageModel("Pak Joko · Koordinator Blok A", "Pembagian pupuk lewat koperasi dimulai minggu depan.", "https://avatar.iran.liara.run/public/25"),
        MessageModel("Bu Rina · Admin", "Jangan lupa perbarui nomor HP di menu Profil.", "https://avatar.iran.liara.run/public/80")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar + tombol back
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Pesan dari Pengurus"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        // Hubungkan ListView dengan MessageAdapter
        val adapter = MessageAdapter(this, messageList)
        binding.listMessageItems.adapter = adapter
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