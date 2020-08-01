package com.hashcode.serverapp.services

class AuthService {
    fun paramsValid(nickname: String, occupation: String): Boolean {
        return nickname.isNotEmpty() && occupation.isNotEmpty()
    }
}