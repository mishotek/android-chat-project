package com.hashcode.serverapp.handlers

import android.content.Context
import android.util.Log
import com.hashcode.serverapp.models.ExtendedUser
import com.hashcode.serverapp.services.BlockUserService
import com.hashcode.serverapp.services.UserList
import com.hashcode.serverapp.services.UserListService
import com.sun.net.httpserver.HttpHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.lang.Error

class BlockUserHandler(private var context: Context) : HttpRequestHandler {
    override fun getHandler(): HttpHandler {
        return HttpHandler { exchange ->
            run {
                // Get request method
                when (exchange!!.requestMethod) {
                    "POST" -> {
                        GlobalScope.launch {
                            val blockUserService = BlockUserService(context)
                            val userListService = UserListService(context)
                            val inputStream = exchange.requestBody
                            val requestBody = streamToString(inputStream)
                            val jsonBody = JSONObject(requestBody)

                            val userId: Long = (jsonBody["userId"] as Int).toLong()
                            val idToBlock: Long = (jsonBody["idToBlock"] as Int).toLong()
                            var query = ""
                            var skip = 0
                            var limit = 10

                            if (jsonBody.has("query")) {
                                query = jsonBody["query"] as String
                            }

                            if (jsonBody.has("skip")) {
                                skip = jsonBody["skip"] as Int
                            }

                            if (jsonBody.has("limit")) {
                                limit = jsonBody["limit"] as Int
                            }

                            blockUserService.block(userId, idToBlock)

                            // New list of users
                            val userList: UserList = userListService.getUsers(userId, query, skip, limit)
                            val usersJson: List<JSONObject> = userList.users.map { user -> userListService.extendedUserToJson(user) }

                            val response = JSONObject()
                            response.put("success", true)
                            response.put("users", usersJson)
                            response.put("count", userList.count)
                            sendResponse(exchange, response.toString())
                        }
                    }
                    else -> {}
                }
            }
        }
    }
}