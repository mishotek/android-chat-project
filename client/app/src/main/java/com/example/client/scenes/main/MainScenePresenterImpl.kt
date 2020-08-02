package com.example.client.scenes.main

import android.content.Intent
import android.util.Log
import com.example.client.entities.ServerStatus
import com.example.client.network.ApiInterface
import com.example.client.scenes.auth.AuthActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainScenePresenterImpl(var view: MainSceneContract.View): MainSceneContract.Presenter {

    private val gateway by lazy {
        ApiInterface.initGateway()
    }

    override fun onCreate() {
        handleServerStatus()
    }

    private fun handleServerStatus() {
        val call = gateway.getServerStatus()
        call.enqueue(object: Callback<ServerStatus> {

            override fun onFailure(call: Call<ServerStatus>, t: Throwable) {
                view.stopProgressIndicator()
                view.setMessage("Server is down!")
            }

            override fun onResponse(call: Call<ServerStatus>, response: Response<ServerStatus>) {
                Log.d("dbg", "success")
                if (response.code() == 200) {
                    val serverStatus = response.body()
                    if (serverStatus != null && serverStatus.success) {
                        view.startAuthActivity()
                    } else {
                        view.stopProgressIndicator()
                        view.setMessage("Server is down!")
                    }
                } else {
                    view.stopProgressIndicator()
                    view.setMessage("Server is down!")
                }
            }
        })
    }

}