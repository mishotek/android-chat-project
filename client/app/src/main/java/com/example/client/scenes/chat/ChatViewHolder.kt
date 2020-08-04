package com.example.client.scenes.chat

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.client.R
import com.example.client.entities.Message

class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun setupView(message: Message, userId: Long) {
        val messageIsIncoming = message.recipientId == userId

        itemView.findViewById<TextView>(R.id.messageText).text = message.message
    }
}