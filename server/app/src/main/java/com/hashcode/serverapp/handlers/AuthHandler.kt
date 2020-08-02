package com.hashcode.serverapp.handlers
import android.content.Context
import android.provider.Settings
import android.util.Log
import com.hashcode.serverapp.services.AuthService
import org.json.JSONObject
import com.sun.net.httpserver.HttpHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AuthHandler(private var context: Context) : HttpRequestHandler {
    override fun getHandler(): HttpHandler {
        return HttpHandler { exchange ->
            run {
                // Get request method
                when (exchange!!.requestMethod) {
                    "POST" -> {
                        val authService = AuthService(context)
                        val inputStream = exchange.requestBody
                        val requestBody = streamToString(inputStream)
                        val jsonBody = JSONObject(requestBody)

                        val nickname: String = jsonBody["nickname"] as String
                        val occupation: String = jsonBody["occupation"] as String
                        val imgBase64: String = jsonBody["imgBase64"] as String

                        if (authService.paramsValid(nickname, occupation)) {
                            val response = JSONObject()
                            val id = authService.registerUser(nickname, occupation, imgBase64)
                            response.put("success", true)
                            response.put("id", id)
                            response.put("nickname", nickname)
                            response.put("occupation", occupation)
                            response.put("imgBase64", imgBase64)
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