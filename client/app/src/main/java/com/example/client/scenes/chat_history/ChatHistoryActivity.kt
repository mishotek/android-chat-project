package com.example.client.scenes.chat_history

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.client.R

class ChatHistoryActivity: AppCompatActivity(), ChatHistorySceneContract.View {


    lateinit var presenter: ChatHistorySceneContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_history)

        presenter = ChatHistoryScenePresenterImpl(this)
    }

}