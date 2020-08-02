package com.hashcode.serverapp.services

import android.content.Context
import com.hashcode.serverapp.database.DatabaseManager
import com.hashcode.serverapp.database.entities.Message
import org.json.JSONObject

class MessageHistoryService(private val context: Context) {

    private val database = DatabaseManager.getDatabase(context).getDao()

    fun getMessages(id1: Long, id2: Long): List<Message> {
        return database.getMessagesBetween(id1, id2)
    }

    fun messageToJson(message: Message): JSONObject {
        val result = JSONObject()

        result.put("id", message.id)
        result.put("senderId", message.senderId)
        result.put("recipientId", message.recipientId)
        result.put("message", message.message)
        result.put("time", message.time)

        return result
    }
}