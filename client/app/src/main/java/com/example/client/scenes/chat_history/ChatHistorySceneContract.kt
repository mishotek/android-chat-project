package com.example.client.scenes.chat_history

import com.example.client.scenes.chat_history.adapters.ChatHistoryListAdapter
import com.example.client.scenes.chat_history.models.ChatItemModel

interface ChatHistorySceneContract {

    interface View {
        fun startChatActivity(userId: Long, chatItemModel: ChatItemModel?)
        fun showToastMessage(message: String)
        fun setNoHistoryLabelVisible(value: Boolean)
    }

    interface Presenter {
        fun getChatHistoryDataSource(userId: Long, completion: (List<ChatItemModel>) -> Unit)
        fun setAdapter(adapter: ChatHistoryListAdapter)
        fun onCreate()
        fun onPause()
        fun onResume()

        var isLoading: Boolean
        fun fetchMoreChatHistory()

        fun onSearch(query: String)
    }

}