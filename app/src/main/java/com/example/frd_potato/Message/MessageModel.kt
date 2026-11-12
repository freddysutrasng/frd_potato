package com.example.frd_potato.Message

// Model data satu pesan: nama pengirim, isi pesan, dan URL foto
data class MessageModel(
    val senderName: String,
    val messageText: String,
    val avatarUrl: String
)