package com.example.frd_potato.Simpanan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.frd_potato.databinding.FragmentSimpananBinding
import android.widget.ArrayAdapter
import android.widget.SimpleAdapter
import android.widget.Toast

class SimpananFragment : Fragment() {

    private var _binding: FragmentSimpananBinding? = null
    private val binding get() = _binding!!

    // Data untuk ArrayAdapter (1 teks per baris)
    private val dataList = listOf(
        "Simpanan Pokok",
        "Simpanan Wajib",
        "Simpanan Sukarela",
        "Simpanan Hari Raya",
        "Simpanan Pendidikan Anak",
        "Simpanan Berjangka 3 Bulan",
        "Simpanan Berjangka 6 Bulan",
        "Simpanan Berjangka 12 Bulan",
        "Simpanan Qurban",
        "Simpanan Hari Tua"
    )

    // Data untuk SimpleAdapter (judul + keterangan)
    private val dataListWithDesc = listOf(
        mapOf("title" to "Simpanan Pokok", "desc" to "Rp 100.000, dibayar sekali saat menjadi anggota"),
        mapOf("title" to "Simpanan Wajib", "desc" to "Rp 50.000 setiap bulan, paling lambat tanggal 25"),
        mapOf("title" to "Simpanan Sukarela", "desc" to "Bebas jumlahnya, bisa ditarik kapan saja"),
        mapOf("title" to "Simpanan Hari Raya", "desc" to "Dicairkan menjelang Idul Fitri atau Natal"),
        mapOf("title" to "Simpanan Pendidikan Anak", "desc" to "Dicairkan saat tahun ajaran baru"),
        mapOf("title" to "Simpanan Berjangka 3 Bulan", "desc" to "Tidak bisa ditarik sebelum 3 bulan"),
        mapOf("title" to "Simpanan Berjangka 6 Bulan", "desc" to "Tidak bisa ditarik sebelum 6 bulan"),
        mapOf("title" to "Simpanan Berjangka 12 Bulan", "desc" to "Tidak bisa ditarik sebelum 12 bulan"),
        mapOf("title" to "Simpanan Qurban", "desc" to "Dicairkan menjelang Idul Adha"),
        mapOf("title" to "Simpanan Hari Tua", "desc" to "Dicairkan saat anggota pensiun")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSimpananBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Simpanan Saya"

        // Adapter = penghubung dataList dengan layout bawaan simple_list_item_1
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            dataList
        )

        // Hubungkan ListView dengan adapter
        binding.listViewItems.adapter = adapter

        // Aksi saat item diklik
        binding.listViewItems.setOnItemClickListener { _, _, position, _ ->
            val selectedItem = dataList[position]
            Toast.makeText(requireContext(), "Kamu memilih: $selectedItem", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}