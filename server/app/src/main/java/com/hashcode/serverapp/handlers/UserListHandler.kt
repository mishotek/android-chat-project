package com.hashcode.serverapp.handlers

import android.content.Context
import com.hashcode.serverapp.models.ExtendedUser
import com.hashcode.serverapp.services.UserList
import com.hashcode.serverapp.services.UserListService
import com.sun.net.httpserver.HttpHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Error

class UserListHandler(private var context: Context) : HttpRequestHandler {
    override fun getHandler(): HttpHandler {
        return HttpHandler { exchange ->
            run {
                // Get request method
                when (exchange!!.requestMethod) {
                    "POST" -> {
                        val userListService = UserListService(context)
                        val inputStream = exchange.requestBody
                        val requestBody = streamToString(inputStream)
                        val jsonBody = JSONObject(requestBody)

                        val userId = (jsonBody["userId"] as Int).toLong()
                        var query = ""
                        var skip = -1
                        var limit = -1

                        if (jsonBody.has("query")) {
                            query = jsonBody["query"] as String
                        }

                        if (jsonBody.has("skip")) {
                            skip = jsonBody["skip"] as Int
                        }

                        if (jsonBody.has("limit")) {
                            limit = jsonBody["limit"] as Int
                        }

                        val userList: UserList = userListService.getUsers(userId, query, skip, limit)
                        val usersJson = JSONArray()
                        userList.users.forEach { user -> usersJson.put(userListService.extendedUserToJson(user)) }

                        val response = JSONObject()
                        response.put("success", true)
                        response.put("users", usersJson)
                        response.put("count", userList.count)
                        sendResponse(exchange, response.toString())
                    }
                }
            }
        }
    }
}