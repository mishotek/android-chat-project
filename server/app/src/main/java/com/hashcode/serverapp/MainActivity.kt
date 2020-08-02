package com.hashcode.serverapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {

    private var serverUp = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        serverButton.setOnClickListener {
            serverUp = if(!serverUp) {
                startService(Intent(this, MainService::class.java))
                serverTextView.text = getString(R.string.server_running)
                serverButton.text = getString(R.string.stop_server)
                true
            } else{
                stopService(Intent(this, MainService::class.java))
                serverTextView.text = getString(R.string.server_down)
                serverButton.text = getString(R.string.start_server)
                false
            }

        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
}
