package com.example.client.scenes.chat_history.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.client.R
import com.example.client.scenes.chat_history.models.ChatItemModel
import com.example.client.scenes.chat_history.view_holders.ChatHistoryListItemViewHolder

class ChatHistoryListAdapter: androidx.recyclerview.widget.RecyclerView.Adapter<ChatHistoryListItemViewHolder>() {

    var chatItems: MutableList<ChatItemModel> = ArrayList<ChatItemModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHistoryListItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cell_chat_history_item, parent, false)
        return ChatHistoryListItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return chatItems.count()
    }

    override fun onBindViewHolder(holder: ChatHistoryListItemViewHolder, position: Int) {
        val model = chatItems[position]
        holder.configure(model)
    }

}