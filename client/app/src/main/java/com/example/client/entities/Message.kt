package com.example.client.entities

data class Message(
    val id: Long,
    val senderId: Long,
    val recipientId: Long,
    val message: String,
    val time: Long
)