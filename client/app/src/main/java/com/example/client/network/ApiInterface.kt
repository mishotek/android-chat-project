package com.example.client.network

import com.example.client.entities.AuthParametersRequest
import com.example.client.entities.AuthParametersResponse
import com.example.client.entities.ServerStatus
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

    companion object {
        fun initGateway() : ApiInterface {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(ApiInterface::class.java)
        }
    }

}