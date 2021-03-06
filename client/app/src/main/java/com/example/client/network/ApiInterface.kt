package com.example.client.network

import com.example.client.entities.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {

    @GET("ping")
    fun getServerStatus(): Call<ServerStatus>

    @POST("auth")
    fun startChatSession(@Body authParams: AuthParametersRequest): Call<AuthParametersResponse>

    @POST("user-list")
    fun getAllActiveUsers(@Body currentUserId: ActiveUsersRequest): Call<ActiveUsers>

    @POST("messages")
    fun getMessages(@Body messageInfo: MessagesRequest): Call<MessagesResponse>

    @POST("send-message")
    fun sendMessage(@Body message: SendMessageRequest): Call<SendMessageResponse>

    @POST("block-user")
    fun blockUser(@Body blockUserParams: BlockUserRequest): Call<BlockUserResponse>

    companion object {

        const val baseUrl = "http://localhost:5000/"
//        const val baseUrl = "http://10.0.2.2:5000/"

        fun initGateway() : ApiInterface {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(ApiInterface::class.java)
        }
    }

}