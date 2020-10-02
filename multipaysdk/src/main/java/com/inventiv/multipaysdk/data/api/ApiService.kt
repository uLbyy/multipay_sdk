package com.inventiv.multipaysdk.data.api

import com.inventiv.multipaysdk.MultiPaySdk
import com.inventiv.multipaysdk.data.api.callback.NetworkCallback
import com.inventiv.multipaysdk.data.model.request.ConfirmOtp
import com.inventiv.multipaysdk.data.model.request.CreateMultinetCard
import com.inventiv.multipaysdk.data.model.request.LoginRequest
import com.inventiv.multipaysdk.data.model.request.ResendOtp
import com.inventiv.multipaysdk.data.model.response.Result


internal class ApiService(private val networkManager: NetworkManager) {

    fun loginRequest(
        loginRequest: LoginRequest,
        networkCallback: NetworkCallback<Result>
    ) {
        networkManager.sendRequest(
            request = loginRequest,
            requestPath = "/SdkLogin",
            responseModel = Result::class.java,
            networkCallback = networkCallback,
            requestBaseUrl = MultiPaySdk.getComponent().environment().loginBaseUrl,
            requestApiServicePath = MultiPaySdk.getComponent().environment().loginApiServicePath
        )
    }

    fun createMultinetCardRequest(
        createMultinetCard: CreateMultinetCard,
        networkCallback: NetworkCallback<Result>
    ) {
        networkManager.sendRequest(
            request = createMultinetCard,
            requestPath = "/CreateMultinetCard",
            responseModel = Result::class.java,
            networkCallback = networkCallback
        )
    }

    fun confirmOtpRequest(
        confirmOtp: ConfirmOtp,
        networkCallback: NetworkCallback<Result>
    ) {
        networkManager.sendRequest(
            confirmOtp,
            "/ConfirmOtpSms",
            Result::class.java,
            networkCallback
        )
    }

    fun resendOtpRequest(
        resendOtp: ResendOtp,
        networkCallback: NetworkCallback<Result>
    ) {
        networkManager.sendRequest(
            resendOtp,
            "/ReSendOtpSms",
            Result::class.java,
            networkCallback
        )
    }
}