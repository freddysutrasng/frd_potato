package com.example.frd_potato.Riwayat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.frd_potato.R
import com.example.frd_potato.databinding.FragmentRiwayatBinding

class RiwayatFragment : Fragment() {

    private var _binding: FragmentRiwayatBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: TransaksiAdapter

    // Data contoh (nanti diganti data dari database)
    private val semuaTransaksi = listOf(
        Transaksi("28 Okt 2026", "Angsuran", "Angsuran pinjaman ke-3", 466667, false),
        Transaksi("25 Okt 2026", "Simpanan", "Setor simpanan wajib Oktober", 50000, true),
        Transaksi("20 Okt 2026", "Simpanan", "Setor simpanan sukarela", 200000, true),
        Transaksi("15 Okt 2026", "Penarikan", "Tarik simpanan sukarela", 100000, false),
        Transaksi("28 Sep 2026", "Angsuran", "Angsuran pinjaman ke-2", 466667, false),
        Transaksi("25 Sep 2026", "Simpanan", "Setor simpanan wajib September", 50000, true),
        Transaksi("28 Agu 2026", "Angsuran", "Angsuran pinjaman ke-1", 466667, false),
        Transaksi("25 Agu 2026", "Simpanan", "Setor simpanan wajib Agustus", 50000, true),
        Transaksi("01 Agu 2026", "Pinjaman", "Pencairan pinjaman 12 bulan", 5000000, true),
        Transaksi("15 Jul 2026", "Simpanan", "Setor simpanan pokok", 100000, true)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRiwayatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Riwayat Transaksi"

        // ===== RIWAYAT + PENCARIAN =====
        adapter = TransaksiAdapter(semuaTransaksi)
        binding.rvRiwayat.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRiwayat.adapter = adapter

        // Setiap huruf diketik -> daftar langsung disaring
        binding.searchRiwayat.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchRiwayat.clearFocus()   // tutup keyboard
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                terapkanFilter()
                return true
            }
        })

        // Pilih chip jenis -> daftar disaring
        binding.chipFilter.setOnCheckedStateChangeListener { _, _ ->
            terapkanFilter()
        }

        // Tampilkan semua transaksi di awal
        terapkanFilter()
    }   // <- akhir onViewCreated

    // Saring daftar berdasarkan kata yang diketik DAN chip yang dipilih
    private fun terapkanFilter() {
        val kata = binding.searchRiwayat.query.toString().trim().lowercase()

        val jenis = when (binding.chipFilter.checkedChipId) {
            R.id.chipSimpanan -> "Simpanan"
            R.id.chipPenarikan -> "Penarikan"
            R.id.chipPinjaman -> "Pinjaman"
            R.id.chipAngsuran -> "Angsuran"
            else -> ""   // chip Semua
        }

        val hasil = semuaTransaksi.filter { transaksi ->
            val cocokJenis = jenis.isEmpty() || transaksi.jenis == jenis
            val cocokKata = kata.isEmpty() ||
                    transaksi.keterangan.lowercase().contains(kata) ||
                    transaksi.tanggal.lowercase().contains(kata) ||
                    transaksi.jenis.lowercase().contains(kata)
            cocokJenis && cocokKata
        }

        adapter.ubahData(hasil)
        binding.tvJumlah.text = "${hasil.size} transaksi"
        binding.tvKosong.visibility = if (hasil.isEmpty()) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}