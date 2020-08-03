package com.hashcode.serverapp.services

import android.content.Context
import android.util.Log
import com.hashcode.serverapp.database.DatabaseManager
import com.hashcode.serverapp.database.entities.Message
import com.hashcode.serverapp.models.ExtendedUser
import org.json.JSONObject

class UserListService(private val context: Context) {

    private val database = DatabaseManager.getDatabase(context).getDao()

    fun getUsers(userId: Long): List<ExtendedUser> {
        val blockedUserIds: List<Long> = database.getBlockedUsers(userId).map { blockedUser -> blockedUser.blockedId }

        return database.getAllUsersBut(userId)
            .map { user -> ExtendedUser(user = user, lastMessage = getLastMessage(userId, user.id)) }
            .filter { user -> !blockedUserIds.any { blockedId -> blockedId == user.user.id } }
    }

    fun extendedUserToJson(extendedUser: ExtendedUser): JSONObject {
        val resultJson = JSONObject()
        val userJson = JSONObject()
        val messageJson = JSONObject()

        userJson.put("nickname", extendedUser.user.nickname)
        userJson.put("occupation", extendedUser.user.occupation)
        userJson.put("imgBase64", extendedUser.user.imgBase64)
        userJson.put("id", extendedUser.user.id.toString())

        resultJson.put("user", userJson)

        if (extendedUser.lastMessage != null) {
            messageJson.put("id", extendedUser.lastMessage.id)
            messageJson.put("message", extendedUser.lastMessage.message)
            messageJson.put("recipientId", extendedUser.lastMessage.recipientId)
            messageJson.put("senderId", extendedUser.lastMessage.senderId)
            messageJson.put("time", extendedUser.lastMessage.time)

            resultJson.put("lastMessage", messageJson)
        }

        return resultJson
    }

    private fun getLastMessage(userId1: Long, userId2: Long): Message? {
        val messages = database.getMessagesBetween(userId1, userId2)

        if (messages.isEmpty()) {
            return null
        }

        var latest = messages[0]

        for (message in messages) {
            if (message.time > latest.time) {
                latest = message
            }
        }

        return latest
    }
}