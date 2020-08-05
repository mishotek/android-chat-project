package com.example.client.scenes.chat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.client.R
import com.example.client.entities.Message

class ChatActivity: AppCompatActivity(), ChatSceneContract.View {
    override var senderId: Long = -1
    override var recipientId: Long = -1
    lateinit var presenter: ChatSceneContract.Presenter

    private var adapter: ChatRecyclerViewAdapter? = null
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        senderId = intent.getLongExtra("userId", -1)
        recipientId = intent.getLongExtra("recipientId", -1)

        presenter = ChatScenePresenterImpl(this)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ChatRecyclerViewAdapter()
        recyclerView.adapter = adapter

        presenter.getMessages()
    }

    override fun showMessages(messages: List<Message>) {
        adapter?.setData(messages, senderId)
    }
}