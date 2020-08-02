package com.example.client.scenes.main

interface MainSceneContract {

    interface View {
        fun startAuthActivity()
        fun setMessage(text: String)
        fun stopProgressIndicator()
    }

    interface Presenter {
        fun checkServerStatus()
    }

}