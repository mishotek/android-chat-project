package com.hashcode.serverapp.handlers

import android.content.Context
import com.hashcode.serverapp.models.ExtendedUser
import com.hashcode.serverapp.services.UserListService
import com.sun.net.httpserver.HttpHandler
import org.json.JSONArray
import org.json.JSONObject

class UserListHandler(private var context: Context) : HttpRequestHandler {
    override fun getHandler(): HttpHandler {
        return HttpHandler { exchange ->
            run {
                // Get request method
                when (exchange!!.requestMethod) {
                    "GET" -> {
                        val userListService = UserListService(context)
                        val inputStream = exchange.requestBody
                        val requestBody = streamToString(inputStream)
                        val jsonBody = JSONObject(requestBody)

                        val userId = (jsonBody["userId"] as Int).toLong()

                        val users: List<ExtendedUser> = userListService.getUsers(userId)
                        val usersJson = JSONArray()
                        users.forEach { user -> usersJson.put(userListService.extendedUserToJson(user)) }

                        val response = JSONObject()
                        response.put("success", true)
                        response.put("users", usersJson)
                        sendResponse(exchange, response.toString())
                    }
                }
            }
        }
    }
}