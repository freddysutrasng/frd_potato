package com.example.frd_potato.Layanan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.frd_potato.R
import com.example.frd_potato.databinding.FragmentLayananKoperasiBinding

class LayananKoperasiFragment : Fragment() {

    private var _binding: FragmentLayananKoperasiBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLayananKoperasiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Layanan SI-KSP sesuai grand design (layar 05 Simpanan dan 06 Ajukan Pinjaman)
        // Cicilan pinjaman = nominal ÷ tenor (contoh nominal Rp 1.000.000)
        val daftarLayanan = listOf(
            LayananModel("Simpanan Pokok", "SIMPANAN", "Rp 100.000, sekali saat jadi anggota", R.drawable.ic_simpanan),
            LayananModel("Simpanan Wajib", "SIMPANAN", "Rp 50.000 per bulan, paling lambat tgl 25", R.drawable.ic_simpanan),
            LayananModel("Simpanan Sukarela", "SIMPANAN", "Bebas jumlahnya, bisa ditarik kapan saja", R.drawable.ic_simpanan),
            LayananModel("Simpanan Hari Raya", "SIMPANAN", "Dicairkan menjelang hari raya", R.drawable.ic_simpanan),
            LayananModel("Pinjaman 3 Bulan", "PINJAMAN", "Rp 1.000.000 → Rp 333.334 / bulan", R.drawable.ic_pinjaman),
            LayananModel("Pinjaman 6 Bulan", "PINJAMAN", "Rp 1.000.000 → Rp 166.667 / bulan", R.drawable.ic_pinjaman),
            LayananModel("Pinjaman 12 Bulan", "PINJAMAN", "Rp 1.000.000 → Rp 83.334 / bulan", R.drawable.ic_pinjaman),
            LayananModel("Simpanan Pendidikan", "SIMPANAN", "Dicairkan saat tahun ajaran baru", R.drawable.ic_simpanan)
        )

        // RecyclerView dengan grid 2 kolom
        binding.rvLayanan.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvLayanan.adapter = LayananAdapter(daftarLayanan) { layanan ->
            Toast.makeText(
                requireContext(),
                "${layanan.nama}: ${layanan.info}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}