package com.example.client.scenes.chat_history

import android.util.Log
import com.example.client.entities.ActiveUsers
import com.example.client.entities.ActiveUsersRequest
import com.example.client.network.ApiInterface
import com.example.client.scenes.chat_history.adapters.ChatHistoryListAdapter
import com.example.client.scenes.chat_history.models.ChatItemModel
import com.example.client.scenes.chat_history.view_holders.ChatHistoryListItemViewHolderDelegate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        val call = gateway.getAllActiveUsers(ActiveUsersRequest(userId))
        call.enqueue(object: Callback<ActiveUsers> {

            override fun onFailure(call: Call<ActiveUsers>, t: Throwable) {
                Log.d("dbg", "FAILURE: couldn't fetch active users")
            }

            override fun onResponse(call: Call<ActiveUsers>, response: Response<ActiveUsers>) {
                val activeUsers = response.body()
                val shouldProceed = response.code() == 200 && activeUsers != null && activeUsers.success
                if (shouldProceed) {
                    val items = activeUsers!!.users.map { it ->
                        it.user.id
                        ChatItemModel(it.user.id.toLong(), it.user.nickname, it.lastMessage?.message ?: "", this@ChatHistoryScenePresenterImpl)
                    }
                    completion(items)
                } else {
                    Log.d("dbg", "Couldn't fetch active users")
                }
            }
        })
    }

    override fun didTapChatItem(recipientId: Long) {
        view.startChatActivity(currentUserId, recipientId)
    }


}