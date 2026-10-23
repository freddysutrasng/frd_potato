package com.example.frd_potato.Home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.frd_potato.WebViewActivity
import com.example.frd_potato.databinding.FragmentHomeBinding

// Fragment Beranda (halaman pertama setelah login)
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Toolbar di Fragment (judul sudah ditulis di XML: logo + SI-KSP)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)

        // Ambil nama yang disimpan saat login
        val sharedPref = requireContext()
            .getSharedPreferences("user_pref", Context.MODE_PRIVATE)
        val nama = sharedPref.getString("nama", "Anggota")
        binding.tvSapaan.text = "Halo, $nama 👋"

        // Tombol ke halaman web
        binding.btnWebView.setOnClickListener {
            startActivity(Intent(requireContext(), WebViewActivity::class.java))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
