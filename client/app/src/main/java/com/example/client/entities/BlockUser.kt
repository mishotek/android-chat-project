package com.example.client.entities

data class BlockUserRequest(
    val userId: Long,
    val idToBlock: Long
)

data class BlockUserResponse(
    val success: Boolean
)