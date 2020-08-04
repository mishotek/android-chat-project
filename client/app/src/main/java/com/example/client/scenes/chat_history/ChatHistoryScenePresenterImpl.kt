package com.example.client.scenes.chat_history

import android.util.Log
import com.example.client.network.ApiInterface
import com.example.client.scenes.chat_history.adapters.ChatHistoryListAdapter
import com.example.client.scenes.chat_history.models.ChatItemModel
import com.example.client.scenes.chat_history.view_holders.ChatHistoryListItemViewHolderDelegate

class ChatHistoryScenePresenterImpl(val view: ChatHistorySceneContract.View): ChatHistorySceneContract.Presenter, ChatHistoryListItemViewHolderDelegate {


    private val gateway by lazy {
        ApiInterface.initGateway()
    }

    private var currentUserId: Long = -1

    override fun getChatHistoryDataSource(userId: Long, completion: (List<ChatItemModel>) -> Unit) {
        currentUserId = userId
        fetchChatHistory(userId) { items ->
            completion(items)
        }
    }

    private fun fetchChatHistory(userId: Long, completion: (List<ChatItemModel>) -> Unit) {
        // TODO: Make service call and fetch real data here
        val items = listOf(
            ChatItemModel(1, "Joni", "Hello there", this),
            ChatItemModel(2, "Bondo", "Hey", this),
            ChatItemModel(3, "Jondo", "Hello darkness my old friend", this),
            ChatItemModel(4, "Tamazi", "CYKA BLYAT!", this),
            ChatItemModel(5, "Arkadi", "ლოთს ღვინო, მწერალს კალამი, ლამაზ გოგონას ჩემგან სალამი!", this)
        )
        completion(items)
    }

    override fun didTapChatItem(recipientId: Long) {
        view.startChatActivity(currentUserId, recipientId)
    }


}