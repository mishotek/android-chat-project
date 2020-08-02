package com.example.client.scenes.auth

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.client.R
import com.example.client.scenes.chat_history.ChatHistoryActivity
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.io.InputStream
import java.util.*


class AuthActivity: AppCompatActivity(), AuthSceneContract.View {

    private var userAvatar: ImageView? = null
    private var nicknameField: EditText? = null
    private var occupationField: EditText? = null
    private var startButton: Button? = null

    private var chosenAvatarImageBase64 = ""

    lateinit var presenter: AuthSceneContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        userAvatar = findViewById(R.id.userAvatar)
        nicknameField = findViewById(R.id.nicknameField)
        occupationField = findViewById(R.id.occupationField)
        startButton = findViewById(R.id.startButton)

        userAvatar?.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"

            startActivityForResult(intent, 0)
        }

        startButton?.setOnClickListener {
            val nickname = nicknameField?.text.toString()
            val occupation = occupationField?.text.toString()

            presenter.onStartClicked(nickname, occupation, chosenAvatarImageBase64)
        }

        presenter = AuthScenePresenterImpl(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                val selectedImageUri = data.data!!
                userAvatar?.setImageURI(selectedImageUri)

                var imageStream: InputStream? = null
                try {
                    imageStream = this.contentResolver.openInputStream(selectedImageUri)
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }
                val yourSelectedImage = BitmapFactory.decodeStream(imageStream)
                chosenAvatarImageBase64 = encodeToBase64(yourSelectedImage) ?: ""
            }
        }
    }

    private fun encodeToBase64(image: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b: ByteArray = baos.toByteArray()
        val imageEncoded = Base64.getEncoder().encodeToString(b)

        Log.d("IMG", imageEncoded)
        return imageEncoded
    }

    override fun showToastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun startChatHistoryActivity() {
        val intent = Intent(this, ChatHistoryActivity::class.java)
        startActivity(intent)
    }
}