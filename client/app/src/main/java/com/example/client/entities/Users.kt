package com.example.client.entities

data class UserWrapper(
    val user: User,
    val lastMessage: Message?
)

data class User(
    val id: String,
    val nickname: String,
    val occupation: String,
    val imgBase64: String
)

data class ActiveUsers(
    val success: Boolean,
    val users: List<UserWrapper>
)

data class ActiveUsersRequest(
    val userId: Long,
    val skip: Int,
    val limit: Int
)
