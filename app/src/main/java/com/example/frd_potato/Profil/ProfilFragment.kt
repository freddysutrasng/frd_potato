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
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.io.File

class ProfilFragment : Fragment() {

    private var _binding: FragmentProfilBinding? = null
    private val binding get() = _binding!!
    // Username yang sedang login (dipakai untuk nama file foto)
    private var usernameAktif = ""

    // Photo Picker: membuka galeri, hasilnya Uri foto yang dipilih
    private val pilihFoto =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                simpanFoto(uri)
            }
        }

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
        // ===== FOTO PROFIL =====
        usernameAktif = username
        tampilkanFoto()

        // Ketuk avatar -> buka Photo Picker (hanya gambar)
        binding.layoutAvatar.setOnClickListener {
            pilihFoto.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }

        // Tekan lama avatar -> tawarkan hapus foto
        binding.layoutAvatar.setOnLongClickListener {
            if (fileFoto().exists()) {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Hapus Foto Profil?")
                    .setMessage("Foto akan dihapus dan diganti inisial nama.")
                    .setNegativeButton("Batal", null)
                    .setPositiveButton("Hapus") { _, _ ->
                        fileFoto().delete()
                        tampilkanFoto()
                    }
                    .show()
            }
            true
        }

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
    // File foto di penyimpanan internal aplikasi, satu file per username
    private fun fileFoto(): File {
        return File(requireContext().filesDir, "foto_$usernameAktif.jpg")
    }

    // Salin foto dari galeri ke penyimpanan aplikasi
    private fun simpanFoto(uri: Uri) {
        val input = requireContext().contentResolver.openInputStream(uri) ?: return
        input.use { masuk ->
            fileFoto().outputStream().use { keluar ->
                masuk.copyTo(keluar)
            }
        }
        tampilkanFoto()
    }

    // Tampilkan foto kalau ada, kalau tidak tampilkan inisial
    private fun tampilkanFoto() {
        val file = fileFoto()
        val bitmap = if (file.exists()) muatFotoKecil(file) else null

        if (bitmap != null) {
            binding.imgFoto.setImageBitmap(bitmap)
            binding.imgFoto.visibility = View.VISIBLE
            binding.tvInisial.visibility = View.GONE
        } else {
            binding.imgFoto.visibility = View.GONE
            binding.tvInisial.visibility = View.VISIBLE
        }
    }

    // Foto dari HP bisa sangat besar, jadi diperkecil dulu agar hemat memori
    private fun muatFotoKecil(file: File): Bitmap? {
        val opsi = BitmapFactory.Options()
        opsi.inJustDecodeBounds = true          // baca ukurannya saja dulu
        BitmapFactory.decodeFile(file.path, opsi)

        var skala = 1
        while (opsi.outWidth / skala > 400 || opsi.outHeight / skala > 400) {
            skala *= 2
        }

        opsi.inJustDecodeBounds = false
        opsi.inSampleSize = skala                // muat versi kecilnya
        return BitmapFactory.decodeFile(file.path, opsi)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
