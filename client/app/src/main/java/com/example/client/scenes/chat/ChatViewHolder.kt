package com.example.client.scenes.chat

import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.client.R
import com.example.client.entities.Message
import java.text.SimpleDateFormat
import java.util.*

class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun setupView(message: Message, userId: Long) {
        val bubbleWrapperView = itemView.findViewById<LinearLayout>(R.id.bubbleWrapper)
        val timeLeftView = itemView.findViewById<TextView>(R.id.timeLeft)
        val timeRightView = itemView.findViewById<TextView>(R.id.timeRight)
        val messageTextView = itemView.findViewById<TextView>(R.id.messageText)

        val messageIsIncoming = message.recipientId == 1.toLong()
//        val messageIsIncoming = message.recipientId == userId

        messageTextView.text = message.message

        if (messageIsIncoming) {
            bubbleWrapperView.gravity = Gravity.LEFT
            bubbleWrapperView.setBackgroundResource(R.drawable.bubble_received)
            timeLeftView.text = ""
            timeRightView.text = getDate(message.time)
            timeRightView.setPadding(12,0,0,0)
        } else {
            bubbleWrapperView.gravity = Gravity.RIGHT
            bubbleWrapperView.setBackgroundResource(R.drawable.bubble_sent)
            timeLeftView.text = getDate(message.time)
            timeRightView.text = ""
            timeRightView.setPadding(0,0,12,0)
        }
    }

    private fun getDate(millSec: Long): String {
        val epoch = millSec / 1000
        val date = Date(epoch * 1000L)
        val sdf = SimpleDateFormat("HH:mm")
        return sdf.format(date)
    }
}