package com.hashcode.serverapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message_table")
data class Message (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val senderId: Long,
    val recipientId: Long,
    val message: String,
    val time: Long
)