package com.example.client.scenes.auth

interface AuthSceneContract {

    interface View {
        fun showToastMessage(message: String)
        fun startChatHistoryActivity()
    }

    interface Presenter {
        fun onStartClicked(nickname: String, occupation: String, photoBase64: String)
    }

}