package com.example.client.scenes.chat

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.client.R
import com.example.client.entities.Message
import java.util.*

class ChatActivity: AppCompatActivity(), ChatSceneContract.View {
    override var senderId: Long = -1
    override var recipientId: Long = -1
    lateinit var presenter: ChatSceneContract.Presenter
    lateinit var timer: Timer

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

        updateMessages()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }

    override fun showMessages(messages: List<Message>) {
        adapter?.setData(messages, senderId)
    }

    private fun updateMessages() {
        timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                presenter.getMessages()
            }
        }, 0, 3000)
    }
}