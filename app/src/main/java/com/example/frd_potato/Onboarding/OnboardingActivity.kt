package com.example.frd_potato.Onboarding

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.frd_potato.AuthActivity
import com.example.frd_potato.databinding.ActivityOnboardingBinding

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding

    // true = dibuka dari Beranda, false = dibuka dari Splash (pertama kali)
    private var dariMenu = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dariMenu = intent.getBooleanExtra("dariMenu", false)

        // 1. Daftar halaman panduan
        val fragments = listOf(
            SelamatDatangFragment(),
            SimpanPinjamFragment(),
            AngsuranRiwayatFragment()
        )

        // 2. Pasang adapter ke ViewPager2
        binding.vpOnboarding.adapter = OnboardingAdapter(this, fragments)

        // 3. Hubungkan DotsIndicator dengan ViewPager2
        binding.dotsOnboarding.attachTo(binding.vpOnboarding)

        // 4. Ubah tombol saat halaman berganti
        binding.vpOnboarding.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    val halamanTerakhir = position == fragments.size - 1
                    binding.btnLanjut.text = when {
                        !halamanTerakhir -> "Lanjut"
                        dariMenu -> "Tutup"
                        else -> "Mulai"
                    }
                    binding.btnLewati.visibility =
                        if (halamanTerakhir) View.INVISIBLE else View.VISIBLE
                }
            }
        )

        // Tombol Lanjut: geser ke halaman berikutnya, di halaman terakhir selesai
        binding.btnLanjut.setOnClickListener {
            val posisi = binding.vpOnboarding.currentItem
            if (posisi < fragments.size - 1) {
                binding.vpOnboarding.currentItem = posisi + 1
            } else {
                selesai()
            }
        }

        // Tombol Lewati: langsung selesai
        binding.btnLewati.setOnClickListener { selesai() }
    }

    private fun selesai() {
        // Simpan bahwa panduan sudah dilihat (app_pref TIDAK dihapus saat logout)
        getSharedPreferences("app_pref", MODE_PRIVATE)
            .edit()
            .putBoolean("panduan_selesai", true)
            .apply()

        // Dari Splash -> lanjut ke Login. Dari Beranda -> cukup ditutup.
        if (!dariMenu) {
            startActivity(Intent(this, AuthActivity::class.java))
        }
        finish()
    }
}