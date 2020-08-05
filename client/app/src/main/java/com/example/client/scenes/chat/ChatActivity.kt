package com.example.client.scenes.chat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.client.R
import com.example.client.entities.Message

class ChatActivity: AppCompatActivity(), ChatSceneContract.View {
    override var senderId: Long = 1
    override var recipientId: Long = 2
    lateinit var presenter: ChatSceneContract.Presenter

    private var adapter: ChatRecyclerViewAdapter? = null
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        presenter = ChatScenePresenterImpl(this)
        presenter.getMessages()

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ChatRecyclerViewAdapter()
        recyclerView.adapter = adapter
    }

    override fun showMessages(messages: List<Message>) {
        adapter?.setData(messages, senderId)
    }
}