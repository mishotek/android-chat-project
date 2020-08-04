package com.example.client.scenes.chat

import com.example.client.entities.Message

interface ChatSceneContract {

    interface View {
        var senderId: Long
        var recipientId: Long

        fun showMessages(messages: List<Message>)
    }

    interface Presenter {
        fun sendMessage(message: String)
        fun getMessages()
    }

}