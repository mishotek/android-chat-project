package com.example.client.scenes.auth

import android.util.Log
import com.example.client.entities.AuthParametersRequest
import com.example.client.entities.AuthParametersResponse
import com.example.client.network.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthScenePresenterImpl(val view: AuthSceneContract.View): AuthSceneContract.Presenter {

    private val gateway by lazy {
        ApiInterface.initGateway()
    }

    override fun onStartClicked(nickname: String, occupation: String, photoBase64: String) {
        if (nickname.isEmpty()) {
            view.showToastMessage("Nickname shouldn't be empty")
            return
        }

        if (occupation.isEmpty()) {
            view.showToastMessage("Occupation shouldn't be empty")
            return
        }

        val call = gateway.startChatSession(AuthParametersRequest(nickname, occupation, photoBase64))
        call.enqueue(object: Callback<AuthParametersResponse> {

            override fun onFailure(call: Call<AuthParametersResponse>, t: Throwable) {
                // Maybe better error handling :)
                view.showToastMessage("Something's wrong with the server!")
            }

            override fun onResponse(call: Call<AuthParametersResponse>, response: Response<AuthParametersResponse>) {
                val authResponse = response.body()
                val shouldProceed = response.code() == 200 && authResponse != null && authResponse.success
                if (shouldProceed) {
                    // TODO: Pass necessary data
                    view.startChatHistoryActivity()
                } else {
                    // Some different kind of feedback is possible here
                    view.showToastMessage("Something went wrong!")
                }
            }
        })
    }

}