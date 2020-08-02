package com.hashcode.serverapp.models

import com.hashcode.serverapp.database.entities.Message
import com.hashcode.serverapp.database.entities.User

data class ExtendedUser (
    val user: User,
    val lastMessage: Message?
)
