package com.example.client.scenes.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.example.client.R
import com.example.client.scenes.auth.AuthActivity
import com.example.client.entities.ServerStatus
import com.example.client.network.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), MainSceneContract.View {

    private var progressIndicator: ProgressBar? = null
    private var titleLabel: TextView? = null

    lateinit var presenter: MainSceneContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressIndicator = findViewById(R.id.progressBar)
        titleLabel = findViewById(R.id.serverStatusLabel)

        presenter = MainScenePresenterImpl(this)
        presenter.onCreate()
    }

    override fun startAuthActivity() {
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
    }

    override fun setMessage(text: String) {
        titleLabel?.text = text
    }

    override fun stopProgressIndicator() {
        progressIndicator?.visibility = View.GONE
    }
}
