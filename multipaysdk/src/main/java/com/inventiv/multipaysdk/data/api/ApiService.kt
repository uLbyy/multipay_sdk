package com.inventiv.multipaysdk.data.api

import com.inventiv.multipaysdk.data.api.callback.NetworkCallback
import com.inventiv.multipaysdk.data.model.request.ConfirmOtp
import com.inventiv.multipaysdk.data.model.request.CreateMultinetCard
import com.inventiv.multipaysdk.data.model.request.LoginRequest
import com.inventiv.multipaysdk.data.model.response.Result


internal class ApiService(private val networkManager: NetworkManager) {

    fun loginRequest(
        loginRequest: LoginRequest,
        networkCallback: NetworkCallback<Result>
    ) {
        networkManager.sendRequest(
            request = loginRequest,
            requestPath = "auth/login",
            responseModel = Result::class.java,
            networkCallback = networkCallback
        )
    }

    fun confirmOtpRequest(
        confirmOtp: ConfirmOtp,
        networkCallback: NetworkCallback<Result>
    ) {
        networkManager.sendRequest(
            request = confirmOtp,
            requestPath = "auth/otp/confirm",
            responseModel = Result::class.java,
            networkCallback = networkCallback
        )
    }

    fun createMultinetCardRequest(
        createMultinetCard: CreateMultinetCard,
        networkCallback: NetworkCallback<Result>
    ) {
        networkManager.sendRequest(
            request = createMultinetCard,
            requestPath = "CreateMultinetCard",
            responseModel = Result::class.java,
            networkCallback = networkCallback
        )
    }
}