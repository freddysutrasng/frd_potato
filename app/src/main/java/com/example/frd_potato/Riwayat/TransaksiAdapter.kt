package com.example.frd_potato.Riwayat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.frd_potato.R
import com.example.frd_potato.databinding.ItemTransaksiBinding
import java.text.NumberFormat
import java.util.Locale

// Adapter = penghubung antara list data Transaksi dan RecyclerView
class TransaksiAdapter(private var data: List<Transaksi>) :
    RecyclerView.Adapter<TransaksiAdapter.TransaksiViewHolder>() {

    // ViewHolder = menyimpan view untuk satu baris (item_transaksi.xml)
    class TransaksiViewHolder(val binding: ItemTransaksiBinding) :
        RecyclerView.ViewHolder(binding.root)

    // Dipanggil saat RecyclerView butuh baris baru
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransaksiViewHolder {
        val binding = ItemTransaksiBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TransaksiViewHolder(binding)
    }

    // Berapa banyak baris yang ditampilkan
    override fun getItemCount(): Int = data.size

    // Mengisi satu baris dengan data pada posisi tertentu
    override fun onBindViewHolder(holder: TransaksiViewHolder, position: Int) {
        val transaksi = data[position]
        val context = holder.itemView.context

        holder.binding.tvKeterangan.text = transaksi.keterangan
        holder.binding.tvTanggal.text = "${transaksi.tanggal} · ${transaksi.jenis}"

        // Tanda + hijau kalau saldo bertambah, - merah kalau berkurang
        val tanda = if (transaksi.masuk) "+ " else "- "
        val warna = if (transaksi.masuk) R.color.hijau else R.color.merah
        holder.binding.tvNominal.text = tanda + rupiah(transaksi.nominal)
        holder.binding.tvNominal.setTextColor(ContextCompat.getColor(context, warna))

        // Ikon: simpanan untuk Simpanan/Penarikan, pinjaman untuk Pinjaman/Angsuran
        val ikon = if (transaksi.jenis == "Simpanan" || transaksi.jenis == "Penarikan") {
            R.drawable.ic_simpanan
        } else {
            R.drawable.ic_pinjaman
        }
        holder.binding.imgJenis.setImageResource(ikon)
    }

    // Ganti isi daftar (dipakai saat pencarian/filter)
    fun ubahData(dataBaru: List<Transaksi>) {
        data = dataBaru
        notifyDataSetChanged()
    }

    // 50000 -> "Rp 50.000"
    private fun rupiah(angka: Long): String {
        val format = NumberFormat.getNumberInstance(Locale.forLanguageTag("id-ID"))
        return "Rp " + format.format(angka)
    }
}