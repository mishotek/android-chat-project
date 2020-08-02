package com.hashcode.serverapp.services

import android.content.Context
import com.hashcode.serverapp.database.DatabaseManager
import com.hashcode.serverapp.database.entities.Message

class MessagingService(private val context: Context) {

    private val database = DatabaseManager.getDatabase(context).getDao()

    fun saveMessage(senderId: Long, recipientId: Long, message: String, time: Long): Long {
        val newMessage = Message(senderId = senderId, recipientId = recipientId, message = message, time = time)
        return database.insertMessage(newMessage)
    }
}