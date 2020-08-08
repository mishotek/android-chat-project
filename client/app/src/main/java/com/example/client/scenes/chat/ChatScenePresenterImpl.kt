package com.example.client.scenes.chat

import android.util.Log
import com.example.client.entities.*
import com.example.client.network.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatScenePresenterImpl(val view: ChatSceneContract.View): ChatSceneContract.Presenter {

    private val gateway by lazy {
        ApiInterface.initGateway()
    }

    override fun sendMessage(message: String) {
        sendMessageRequest(view.senderId, view.recipientId, message) { messageObj ->

        }
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

    private fun sendMessageRequest(senderId: Long, recipientId: Long, message: String, completion: (Message?) -> Unit) {
        val call = gateway.sendMessage(SendMessageRequest(senderId, recipientId, message))
        call.enqueue(object: Callback<SendMessageResponse> {

            override fun onFailure(call: Call<SendMessageResponse>, t: Throwable) {
                Log.d("dbg", "FAILURE: couldn't fetch messages")
            }

            override fun onResponse(call: Call<SendMessageResponse>, response: Response<SendMessageResponse>) {
                val message = response.body()
                val shouldProceed = response.code() == 200 && message != null && message.success
                if (shouldProceed) {
                    completion(message?.message)
                } else {
                    Log.d("dbg", "Couldn't fetch active users")
                }
            }
        })
    }
}