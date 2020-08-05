package com.example.client.scenes.chat

import android.util.Log
import com.example.client.entities.*
import com.example.client.network.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatScenePresenterImpl(val view: ChatSceneContract.View): ChatSceneContract.Presenter {

    private val messages: ArrayList<Message> = ArrayList()
    private val gateway by lazy {
        ApiInterface.initGateway()
    }

    init {
        messages.add(Message(id = 1, senderId = 1, recipientId = 2, message = "Privet", time = 1596642725319))
        messages.add(Message(id = 2, senderId = 2, recipientId = 1, message = "Zdarova", time = 1596642725319))
        messages.add(Message(id = 3, senderId = 1, recipientId = 2, message = "Kak jizn maladaia?", time = 1596642725319))
    }

    override fun sendMessage(message: String) {
        messages.add(Message(id = 10, senderId = view.senderId, recipientId = view.recipientId, message = message, time = 10))

        view.showMessages(messages)
    }

    override fun getMessages() {
        fetchMessages(view.senderId, view.recipientId) { messages -> view.showMessages(messages) }
    }

    private fun fetchMessages(senderId: Long, recipientId: Long, completion: (List<Message>) -> Unit) {
        val call = gateway.getMessages(MessagesRequest(senderId, recipientId))

        call.enqueue(object: Callback<MessagesResponse> {

            override fun onFailure(call: Call<MessagesResponse>, t: Throwable) {
                Log.d("dbg", "FAILURE: couldn't fetch messages")
            }

            override fun onResponse(call: Call<MessagesResponse>, response: Response<MessagesResponse>) {
                val messages = response.body()
                val shouldProceed = response.code() == 200 && messages != null && messages.success
                if (shouldProceed) {
                    completion(messages?.messages ?: ArrayList())
                } else {
                    Log.d("dbg", "Couldn't fetch active users")
                }
            }
        })
    }
}