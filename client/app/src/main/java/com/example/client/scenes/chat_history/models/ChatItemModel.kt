package com.example.client.scenes.chat_history.models

import com.example.client.scenes.chat_history.view_holders.ChatHistoryListItemViewHolderDelegate

class ChatItemModel(
    val userId: Long,
    val nickname: String,
    val lastMessage: String,
    val delegate: ChatHistoryListItemViewHolderDelegate
)