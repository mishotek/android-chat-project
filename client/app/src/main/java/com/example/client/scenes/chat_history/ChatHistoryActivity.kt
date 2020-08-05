package com.example.client.scenes.chat_history

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.client.R
import com.example.client.scenes.chat.ChatActivity
import com.example.client.scenes.chat_history.adapters.ChatHistoryListAdapter

class ChatHistoryActivity: AppCompatActivity(), ChatHistorySceneContract.View {


    private var chatHistoryList: RecyclerView? = null
    private var noHistoryLabel: TextView? = null

    lateinit var presenter: ChatHistorySceneContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_history)

        noHistoryLabel = findViewById(R.id.noHistoryLabel)
        chatHistoryList = findViewById(R.id.chatHistoryList)
        chatHistoryList?.layoutManager = LinearLayoutManager(this)

        val currentUserId = intent.getLongExtra("userId", -1)

        presenter = ChatHistoryScenePresenterImpl(this)
        presenter.onCreate()
        presenter.getChatHistoryDataSource(currentUserId) { items ->
            val adapter = ChatHistoryListAdapter()
            adapter.chatItems = items
            chatHistoryList?.adapter = adapter
            presenter.setAdapter(adapter)
        }
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun showToastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun setNoHistoryLabelVisible(value: Boolean) {
        noHistoryLabel?.visibility = if (value) View.VISIBLE else View.GONE
    }

    override fun startChatActivity(userId: Long, recipientId: Long) {
        val intent = Intent(this, ChatActivity::class.java)
        intent.putExtra("userId", userId)
        intent.putExtra("recipientId", recipientId)
        startActivity(intent)
    }

}