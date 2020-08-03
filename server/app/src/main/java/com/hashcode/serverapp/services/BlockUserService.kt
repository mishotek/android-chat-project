package com.hashcode.serverapp.services

import android.content.Context
import android.util.Log
import com.hashcode.serverapp.database.DatabaseManager
import com.hashcode.serverapp.database.entities.BlockedUsers

class BlockUserService(private val context: Context) {

    private val database = DatabaseManager.getDatabase(context).getDao()

    fun block(userId: Long, idToBlock: Long) {
        val blockedUsers = BlockedUsers(blockerId = userId, blockedId = idToBlock)
        database.blockUser(blockedUsers)
    }
}