package com.example.client.entities

data class User(
    val id: Long,
    val nickname: String,
    val occupation: String,
    val imgBase64: String,
    val lastMessage: String?
)

data class ActiveUsers(
    val success: Boolean,
    val users: List<User>
)

data class ActiveUsersRequest(
    val userId: Long
)