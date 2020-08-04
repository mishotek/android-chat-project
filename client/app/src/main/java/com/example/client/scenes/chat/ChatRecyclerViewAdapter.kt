package com.example.client.scenes.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.client.R
import com.example.client.entities.Message

class ChatRecyclerViewAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val messages: ArrayList<Message> = ArrayList()
    private var userId: Long = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val chatView: View = LayoutInflater.from(parent.context).inflate(R.layout.chat_bubble, parent, false)
        return ChatViewHolder(chatView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val cellViewHolder: ChatViewHolder = holder as ChatViewHolder
        cellViewHolder.setupView(messages[position], userId)
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    fun setData(newMessages: List<Message>, newUserId: Long) {
        messages.clear()
        messages.addAll(newMessages)
        userId = newUserId

        notifyDataSetChanged()
    }
}