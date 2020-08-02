package com.hashcode.serverapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nickname: String,
    val occupation: String,
    val imgBase64: String
)