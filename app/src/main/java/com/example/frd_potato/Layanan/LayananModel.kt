package com.example.frd_potato.Layanan

// Satu layanan koperasi (produk simpanan atau produk pinjaman)
data class LayananModel(
    val nama: String,      // nama layanan, contoh "Simpanan Wajib"
    val kategori: String,  // "SIMPANAN" atau "PINJAMAN"
    val info: String,      // keterangan singkat / nominal
    val iconRes: Int       // ikon dari res/drawable
)