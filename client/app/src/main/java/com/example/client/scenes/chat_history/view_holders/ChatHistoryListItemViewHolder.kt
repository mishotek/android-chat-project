package com.example.client.scenes.chat_history.view_holders

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.client.R
import com.example.client.scenes.chat_history.models.ChatItemModel
import kotlinx.android.synthetic.main.cell_chat_history_item.view.*

interface ChatHistoryListItemViewHolderDelegate {
    fun didTapChatItem(model: ChatItemModel?)
}

class ChatHistoryListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    private var nicknameLabel: TextView? = null
    private var lastMessageLabel: TextView? = null
    private var model: ChatItemModel? = null

    var delegate: ChatHistoryListItemViewHolderDelegate? = null

    init {
        itemView.setOnClickListener {
            delegate?.didTapChatItem(this.model)
        }
        nicknameLabel = itemView.findViewById(R.id.chatItemNickname)
        lastMessageLabel = itemView.findViewById(R.id.chatItemMessage)
    }

    fun configure(model: ChatItemModel) {
        nicknameLabel?.text = model.nickname
        lastMessageLabel?.text = model.lastMessage
        delegate = model.delegate
        this.model = model
    }

}