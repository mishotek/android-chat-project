package com.hashcode.serverapp.handlers
import com.hashcode.serverapp.services.AuthService
import org.json.JSONObject
import com.sun.net.httpserver.HttpHandler

class AuthHandler : HttpRequestHandler {
    override fun getHandler(): HttpHandler {
        return HttpHandler { exchange ->
            run {
                // Get request method
                when (exchange!!.requestMethod) {
                    "POST" -> {
                        val authService = AuthService()
                        val inputStream = exchange.requestBody

                        val requestBody = streamToString(inputStream)
                        val jsonBody = JSONObject(requestBody)

                        val nickname: String = jsonBody["nickname"] as String
                        val occupation: String = jsonBody["occupation"] as String
                        val imgBase64: String = jsonBody["occupation"] as String

                        if (authService.paramsValid(nickname, occupation)) {
                            val response = JSONObject()
                            response.put("success", true)
                            sendResponse(exchange, response.toString())
                        } else {
                            val response = JSONObject()
                            response.put("success", false)
                            sendResponse(exchange, response.toString())
                        }
                    }
                }
            }
        }
    }
}