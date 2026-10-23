package com.example.frd_potato.Profil

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.frd_potato.AuthActivity
import com.example.frd_potato.ProfilDeveloperActivity
import com.example.frd_potato.databinding.FragmentProfilBinding

class ProfilFragment : Fragment() {

    private var _binding: FragmentProfilBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Profil Saya"

        val sharedPref = requireContext()
            .getSharedPreferences("user_pref", Context.MODE_PRIVATE)

        // Tampilkan data anggota yang sedang login
        val nama = sharedPref.getString("nama", "Anggota") ?: "Anggota"
        val username = sharedPref.getString("username", "") ?: ""
        binding.tvNama.text = nama
        binding.tvUsername.text = "@$username"
        binding.tvInisial.text = nama.take(1).uppercase()

        // Ke halaman Profil Developer
        binding.btnProfilDeveloper.setOnClickListener {
            startActivity(Intent(requireContext(), ProfilDeveloperActivity::class.java))
        }

        // Logout: konfirmasi dulu, lalu hapus status login
        binding.btnLogout.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Keluar")
                .setMessage("Apakah kamu yakin ingin keluar dari akun ini?")
                .setPositiveButton("Ya") { dialog, _ ->
                    val editor = sharedPref.edit()
                    editor.clear()
                    editor.apply()

                    dialog.dismiss()
                    startActivity(Intent(requireContext(), AuthActivity::class.java))
                    requireActivity().finish() // tutup BaseActivity
                }
                .setNegativeButton("Tidak") { dialog, _ -> dialog.dismiss() }
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
