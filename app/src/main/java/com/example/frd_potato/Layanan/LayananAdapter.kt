package com.example.frd_potato.Layanan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.frd_potato.databinding.ItemLayananBinding

class LayananAdapter(
    private val daftarLayanan: List<LayananModel>,
    private val onItemClick: (LayananModel) -> Unit
) : RecyclerView.Adapter<LayananAdapter.LayananViewHolder>() {

    // ViewHolder menyimpan referensi view satu item
    inner class LayananViewHolder(val binding: ItemLayananBinding) :
        RecyclerView.ViewHolder(binding.root)

    // Membuat tampilan item dari item_layanan.xml
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LayananViewHolder {
        val binding = ItemLayananBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return LayananViewHolder(binding)
    }

    // Mengisi data ke item sesuai posisi
    override fun onBindViewHolder(holder: LayananViewHolder, position: Int) {
        val layanan = daftarLayanan[position]
        holder.binding.tvNamaLayanan.text = layanan.nama
        holder.binding.tvKategori.text = layanan.kategori
        holder.binding.tvInfoLayanan.text = layanan.info
        holder.binding.imgLayanan.setImageResource(layanan.iconRes)

        holder.itemView.setOnClickListener { onItemClick(layanan) }
    }

    override fun getItemCount(): Int = daftarLayanan.size
}