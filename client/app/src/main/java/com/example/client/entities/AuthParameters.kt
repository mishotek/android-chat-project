package com.example.client.entities

data class AuthParametersRequest(
    val nickname: String,
    val occupation: String,
    val imgBase64: String
)

data class AuthParametersResponse(
    val success: Boolean,
    val id: Long,
    val nickname: String,
    val occupation: String,
    val imgBase64: String
)