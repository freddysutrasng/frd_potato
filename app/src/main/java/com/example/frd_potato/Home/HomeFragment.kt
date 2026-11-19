package com.example.frd_potato.Home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.frd_potato.Home.pertemuan_9.NinthActivity
import com.example.frd_potato.Layanan.LayananActivity
import com.example.frd_potato.Message.MessageActivity
import com.example.frd_potato.Onboarding.OnboardingActivity
import com.example.frd_potato.R
import com.example.frd_potato.WebViewActivity
import com.example.frd_potato.databinding.FragmentHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

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

        // Aktifkan option menu (ikon lonceng + ⓘ) milik Fragment ini
        setHasOptionsMenu(true)

        // Ambil nama yang disimpan saat login
        val sharedPref = requireContext()
            .getSharedPreferences("user_pref", Context.MODE_PRIVATE)
        val nama = sharedPref.getString("nama", "Anggota")
        binding.tvSapaan.text = "Halo, $nama 👋"

        // Tombol ke halaman web
        binding.btnWebView.setOnClickListener {
            startActivity(Intent(requireContext(), WebViewActivity::class.java))
        }

        // ===== Menu Cepat (GridLayout 4 kolom, sesuai grand design) =====
        // Baris 1: Setor, Tarik, Pinjam, Angsuran
        binding.menuSetor.setOnClickListener { pindahMenu(R.id.simpanan) }
        binding.menuTarik.setOnClickListener { pindahMenu(R.id.simpanan) }
        binding.menuPinjam.setOnClickListener {
            startActivity(Intent(requireContext(), NinthActivity::class.java))
        }
        binding.menuAngsuran.setOnClickListener { pindahMenu(R.id.pinjaman) }

        // Baris 2: Riwayat, Pesan, Layanan, Panduan
        binding.menuRiwayat.setOnClickListener { pindahMenu(R.id.riwayat) }
        binding.menuPesan.setOnClickListener {
            startActivity(Intent(requireContext(), MessageActivity::class.java))
        }
        binding.menuLayanan.setOnClickListener { bukaLayanan(2) }   // tab Layanan
        binding.menuPanduan.setOnClickListener { bukaPanduan() }
    }

    // Membuka halaman Info & Layanan pada tab tertentu (0 = Notifikasi, 2 = Layanan)
    private fun bukaLayanan(tab: Int) {
        val intent = Intent(requireContext(), LayananActivity::class.java)
        intent.putExtra("tab", tab)
        startActivity(intent)
    }

    // Memindahkan tab Bottom Navigation milik BaseActivity
    private fun pindahMenu(menuId: Int) {
        requireActivity()
            .findViewById<BottomNavigationView>(R.id.bottom_nav_view)
            .selectedItemId = menuId
    }

    // Membuka On Board Screen dari Beranda (bukan dari Splash)
    private fun bukaPanduan() {
        val intent = Intent(requireContext(), OnboardingActivity::class.java)
        intent.putExtra("dariMenu", true)
        startActivity(intent)
    }

    // Pasang ikon lonceng + ⓘ di Toolbar
    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_beranda, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    // Aksi saat ikon di Toolbar ditekan
    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_notifikasi -> {
                bukaLayanan(0)   // tab Notifikasi
                true
            }
            R.id.action_panduan -> {
                bukaPanduan()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}