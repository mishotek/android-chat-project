package com.example.client.scenes.chat_history

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.client.R
import com.example.client.scenes.chat.ChatActivity
import com.example.client.scenes.chat_history.adapters.ChatHistoryListAdapter

class ChatHistoryActivity: AppCompatActivity(), ChatHistorySceneContract.View {


    private var chatHistoryList: RecyclerView? = null

    lateinit var presenter: ChatHistorySceneContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_history)

        chatHistoryList = findViewById(R.id.chatHistoryList)
        chatHistoryList?.layoutManager = LinearLayoutManager(this)

        val currentUserId = intent.getLongExtra("userId", -1)

        presenter = ChatHistoryScenePresenterImpl(this)
        presenter.getChatHistoryDataSource(currentUserId) { items ->
            val adapter = ChatHistoryListAdapter()
            adapter.chatItems = items
            chatHistoryList?.adapter = adapter
        }
    }

    override fun startChatActivity(userId: Long, recipientId: Long) {
        val intent = Intent(this, ChatActivity::class.java)
        intent.putExtra("userId", userId)
        intent.putExtra("recipientId", recipientId)
        startActivity(intent)
    }

}