package com.inventiv.multipaysdk.data.api

import com.inventiv.multipaysdk.data.api.callback.NetworkCallback
import com.inventiv.multipaysdk.data.model.request.LoginGsm
import com.inventiv.multipaysdk.data.model.response.Result


internal class ApiService(private val networkManager: NetworkManager) {

    fun loginRequest(
        loginGsm: LoginGsm,
        networkCallback: NetworkCallback<Result>
    ) {
        networkManager.sendRequest(
            loginGsm,
            "/SdkLogin",
            Result::class.java,
            networkCallback
        )
    }
}