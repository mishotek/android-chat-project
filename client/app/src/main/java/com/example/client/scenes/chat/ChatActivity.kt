package com.example.client.scenes.chat

import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.client.R
import com.example.client.entities.Message
import android.util.Base64
import java.util.*


class ChatActivity: AppCompatActivity(), ChatSceneContract.View {
    override var senderId: Long = -1
    override var recipientId: Long = -1
    lateinit var presenter: ChatSceneContract.Presenter
    lateinit var timer: Timer

    var newMessageText = ""

    private var adapter: ChatRecyclerViewAdapter? = null
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        setUpList()
        setUpHeader()
        listenToTyping()
        listenToSend()
        updateMessages()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }

    override fun showMessages(messages: List<Message>) {
        adapter?.setData(messages, senderId)
    }

    private fun setUpHeader() {
        val nickname = intent.getStringExtra("nickname")
        val occupation = intent.getStringExtra("occupation")
        val imgBase64 = intent.getStringExtra("imgBase64")

        findViewById<TextView>(R.id.headerName).text = nickname
        findViewById<TextView>(R.id.headerOccupation).text = occupation
        findViewById<LinearLayout>(R.id.backButton).setOnClickListener { event -> finish() }

        if (imgBase64 != null && imgBase64.isNotEmpty()) {
            val imageAsBytes: ByteArray = Base64.decode(imgBase64.toByteArray(), Base64.DEFAULT)

            val image: ImageView = findViewById<View>(R.id.headerAvatar) as ImageView

            image.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.size))
        }
    }

    private fun setUpList() {
        senderId = intent.getLongExtra("userId", -1)
        recipientId = intent.getLongExtra("recipientId", -1)

        presenter = ChatScenePresenterImpl(this)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ChatRecyclerViewAdapter()
        recyclerView.adapter = adapter
    }

    private fun updateMessages() {
        timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                presenter.getMessages()
            }
        }, 0, 3000)
    }

    private fun listenToTyping() {
        findViewById<EditText>(R.id.messageInput).addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                newMessageText = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
            }
        })
    }

    private fun listenToSend() {
        findViewById<LinearLayout>(R.id.sendButton).setOnClickListener { event -> onSend() }
    }

    private fun onSend() {
        presenter.sendMessage(newMessageText)
        findViewById<EditText>(R.id.messageInput).setText("")
        newMessageText = ""
        presenter.getMessages()
    }
}