package com.hashcode.serverapp.services

import android.content.Context
import com.hashcode.serverapp.database.DatabaseManager
import com.hashcode.serverapp.database.MessagingDatabase
import com.hashcode.serverapp.database.entities.User

class AuthService(private val context: Context) {

    private val database = DatabaseManager.getDatabase(context).getDao()

    fun paramsValid(nickname: String, occupation: String): Boolean {
        return nickname.isNotEmpty() && occupation.isNotEmpty()
    }

    fun registerUser(nickname: String, occupation: String, imgBase64: String): Long {
        val user = User(nickname = nickname, occupation = occupation, imgBase64 = imgBase64)
        return database.insertUser(user)
    }
}