package com.inventiv.multipaysdk.data.api

import com.inventiv.multipaysdk.data.api.callback.NetworkCallback
import com.inventiv.multipaysdk.data.model.request.LoginRequest
import com.inventiv.multipaysdk.data.model.response.Result


internal class ApiService(private val networkManager: NetworkManager) {

    fun networkManager() = networkManager

    fun loginRequest(
        loginRequest: LoginRequest,
        networkCallback: NetworkCallback<Result>
    ) {
        networkManager.sendRequest(
            loginRequest,
            "/SdkLogin",
            Result::class.java,
            networkCallback
        )
    }
}