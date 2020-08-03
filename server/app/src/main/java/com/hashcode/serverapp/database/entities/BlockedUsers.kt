package com.hashcode.serverapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "blocked_user_table")
data class BlockedUsers (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val blockerId: Long,
    val blockedId: Long
)