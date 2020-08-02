package com.hashcode.serverapp.handlers

import android.content.Context
import com.hashcode.serverapp.database.entities.Message
import com.hashcode.serverapp.services.MessageHistoryService
import com.sun.net.httpserver.HttpHandler
import org.json.JSONObject

class MessageHistoryHandler(private var context: Context) : HttpRequestHandler {
    override fun getHandler(): HttpHandler {
        return HttpHandler { exchange ->
            run {
                // Get request method
                when (exchange!!.requestMethod) {
                    "GET" -> {
                        val messageHistoryService = MessageHistoryService(context)
                        val inputStream = exchange.requestBody
                        val requestBody = streamToString(inputStream)
                        val jsonBody = JSONObject(requestBody)

                        val senderId = (jsonBody["senderId"] as Int).toLong()
                        val recipientId: Long = (jsonBody["recipientId"] as Int).toLong()

                        val messages: List<Message> = messageHistoryService.getMessages(senderId, recipientId)
                        val messagesJson: List<JSONObject> = messages.map{message -> messageHistoryService.messageToJson(message)}

                        val response = JSONObject()
                        response.put("success", true)
                        response.put("messages", messagesJson)

                        sendResponse(exchange, response.toString())
                    }
                }
            }
        }
    }
}