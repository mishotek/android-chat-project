package com.hashcode.serverapp.services

import android.content.Context
import android.util.Log
import com.hashcode.serverapp.database.DatabaseManager
import com.hashcode.serverapp.database.entities.Message
import com.hashcode.serverapp.database.entities.User
import com.hashcode.serverapp.models.ExtendedUser
import org.json.JSONObject

class UserListService(private val context: Context) {

    private val database = DatabaseManager.getDatabase(context).getDao()

    fun getUsers(userId: Long, query: String, skip: Int, limit: Int): List<ExtendedUser> {
        val blockedUserIds: List<Long> = database.getBlockedUsers(userId).map { blockedUser -> blockedUser.blockedId }

        val users = filterByQuery(database.getAllUsersBut(userId), query)
        val validUsers = users
            .map { user -> ExtendedUser(user = user, lastMessage = getLastMessage(userId, user.id)) }
            .filter { user -> !blockedUserIds.any { blockedId -> blockedId == user.user.id } }

        return sublist(validUsers, skip, limit)
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

    private fun filterByQuery(users: List<User>, query: String): List<User> {
        if (query.isEmpty()) {
            return users
        }

        return users.filter { user -> user.nickname.indexOf(query) != -1 }
    }

    private fun sublist(users: List<ExtendedUser>, skip: Int, limit: Int): List<ExtendedUser> {
        if (skip == -1 && limit == -1) {
            return users
        }

        if (skip >= users.size) {
            return ArrayList()
        }

        var _skip = skip

        if (skip == -1) {
            _skip = 0
        }

        var _end = _skip + limit

        if (_end > users.size) {
            _end = users.size
        }

        return users.subList(_skip, _end)
    }
}