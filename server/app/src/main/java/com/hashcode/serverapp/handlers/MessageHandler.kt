package com.hashcode.serverapp.handlers

import android.content.Context
import com.hashcode.serverapp.services.MessagingService
import com.sun.net.httpserver.HttpHandler
import org.json.JSONObject

class MessageHandler(private var context: Context) : HttpRequestHandler {
    override fun getHandler(): HttpHandler {
        return HttpHandler { exchange ->
            run {
                // Get request method
                when (exchange!!.requestMethod) {
                    "POST" -> {
                        val messagingService = MessagingService(context)
                        val inputStream = exchange.requestBody
                        val requestBody = streamToString(inputStream)
                        val jsonBody = JSONObject(requestBody)

                        val senderId = (jsonBody["senderId"] as Int).toLong()
                        val recipientId: Long = (jsonBody["recipientId"] as Int).toLong()
                        val message: String = jsonBody["message"] as String
                        val time: Long = System.currentTimeMillis()

                        val messageId = messagingService.saveMessage(senderId, recipientId, message, time)

                        val messageJson = JSONObject()
                        messageJson.put("senderId", senderId)
                        messageJson.put("recipientId", recipientId)
                        messageJson.put("message", message)
                        messageJson.put("time", time)
                        messageJson.put("messageId", messageId)

                        val response = JSONObject()
                        response.put("success", true)
                        response.put("message", messageJson)


                        sendResponse(exchange, response.toString())
                    }
                }
            }
        }
    }
}