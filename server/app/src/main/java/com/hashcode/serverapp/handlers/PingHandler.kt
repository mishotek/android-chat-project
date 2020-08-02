package com.hashcode.serverapp.handlers

import com.sun.net.httpserver.HttpHandler
import org.json.JSONObject

class PingHandler : HttpRequestHandler {
    override fun getHandler(): HttpHandler {
        return HttpHandler { exchange ->
            run {
                // Get request method
                when (exchange!!.requestMethod) {
                    "GET" -> {
                        val response = JSONObject()
                        response.put("success", true)
                        response.put("data", "Server is up and running")

                        sendResponse(exchange, response.toString())
                    }
                }
            }
        }
    }
}