package com.hashcode.serverapp.handlers

import com.sun.net.httpserver.HttpHandler

class PingHandler : HttpRequestHandler {
    override fun getHandler(): HttpHandler {
        return HttpHandler { exchange ->
            run {
                // Get request method
                when (exchange!!.requestMethod) {
                    "GET" -> {
                        sendResponse(exchange, "Server is up and running")
                    }
                }
            }
        }
    }
}