package com.example.client.scenes.chat_history

import com.example.client.scenes.chat_history.adapters.ChatHistoryListAdapter
import com.example.client.scenes.chat_history.models.ChatItemModel

interface ChatHistorySceneContract {

    interface View {

    }

    interface Presenter {
        fun getChatHistoryDataSource(userId: Long, completion: (List<ChatItemModel>) -> Unit)
    }

}