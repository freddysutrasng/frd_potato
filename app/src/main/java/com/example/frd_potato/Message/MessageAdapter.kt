package com.example.frd_potato.Message

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.bumptech.glide.Glide
import com.example.frd_potato.R
import com.example.frd_potato.databinding.ItemMessageBinding
import com.google.android.material.snackbar.Snackbar

// Adapter = penghubung List<MessageModel> dengan tampilan item_message.xml
class MessageAdapter(
    context: Context,
    private val messages: List<MessageModel>
) : ArrayAdapter<MessageModel>(context, 0, messages) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = ItemMessageBinding.inflate(LayoutInflater.from(context), parent, false)
        val view = binding.root

        val data = messages[position]

        // Glide memuat foto dari URL ke ImageView
        Glide.with(context)
            .load(data.avatarUrl)
            .placeholder(R.drawable.bg_avatar)   // tampil selama foto dimuat
            .error(R.drawable.bg_avatar)         // tampil kalau internet mati
            .into(binding.avatarImg)

        binding.textSender.text = data.senderName
        binding.textMessage.text = data.messageText

        // Klik item -> Snackbar (event klik sekarang ada di Adapter)
        view.setOnClickListener {
            Snackbar.make(
                parent,
                "Pesan dari ${data.senderName}: ${data.messageText}",
                Snackbar.LENGTH_SHORT
            ).show()
        }

        return view
    }
}