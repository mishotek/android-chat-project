package com.example.client.scenes.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.client.R
import com.example.client.scenes.chat_history.ChatHistoryActivity

class AuthActivity: AppCompatActivity(), AuthSceneContract.View {

    private var nicknameField: EditText? = null
    private var occupationField: EditText? = null
    private var startButton: Button? = null

    lateinit var presenter: AuthSceneContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        nicknameField = findViewById(R.id.nicknameField)
        occupationField = findViewById(R.id.occupationField)
        startButton = findViewById(R.id.startButton)


        startButton?.setOnClickListener {
            val nickname = nicknameField?.text.toString()
            val occupation = occupationField?.text.toString()
            // TODO: Add photo chooser

            presenter.onStartClicked(nickname, occupation, "")
        }

        presenter = AuthScenePresenterImpl(this)
    }

    override fun showToastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun startChatHistoryActivity() {
        val intent = Intent(this, ChatHistoryActivity::class.java)
        startActivity(intent)
    }
}