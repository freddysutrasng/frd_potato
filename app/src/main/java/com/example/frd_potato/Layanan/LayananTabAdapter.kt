package com.example.frd_potato.Layanan

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

// Mengatur Fragment mana yang tampil di setiap tab
class LayananTabAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    // Jumlah tab
    override fun getItemCount(): Int = 3

    // Fragment untuk setiap posisi tab
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> NotifikasiFragment()
            1 -> JadwalCicilanFragment()
            2 -> LayananKoperasiFragment()
            else -> throw IllegalStateException("Posisi tidak valid")
        }
    }
}