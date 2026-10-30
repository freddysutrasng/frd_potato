package com.example.frd_potato.Riwayat

// Bentuk data satu transaksi koperasi
data class Transaksi(
    val tanggal: String,     // contoh "28 Okt 2026"
    val jenis: String,       // Simpanan, Penarikan, Pinjaman, atau Angsuran
    val keterangan: String,  // contoh "Setor simpanan wajib Oktober"
    val nominal: Long,       // dalam Rupiah
    val masuk: Boolean       // true = saldo bertambah (+), false = berkurang (-)
)