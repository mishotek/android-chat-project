package com.example.client.scenes.chat

import com.example.client.entities.Message

class ChatScenePresenterImpl(val view: ChatSceneContract.View): ChatSceneContract.Presenter {

    private val messages: ArrayList<Message> = ArrayList()

    init {
        messages.add(Message(id = 1, senderId = 1, recipientId = 2, message = "Privet", time = 1))
        messages.add(Message(id = 2, senderId = 2, recipientId = 1, message = "Zdarova", time = 2))
        messages.add(Message(id = 3, senderId = 1, recipientId = 2, message = "Kak jizn maladaia?", time = 3))
    }

    override fun sendMessage(message: String) {
        messages.add(Message(id = 10, senderId = view.senderId, recipientId = view.recipientId, message = message, time = 10))

        view.showMessages(messages)
    }

    override fun getMessages() {
        view.showMessages(messages)
    }
}