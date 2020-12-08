package com.inventiv.multipaysdk.data.api

import com.inventiv.multipaysdk.data.api.callback.NetworkCallback
import com.inventiv.multipaysdk.data.model.request.*
import com.inventiv.multipaysdk.data.model.response.Result
import com.inventiv.multipaysdk.data.model.singleton.MultiPayUser


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

    fun walletRequest(
        networkCallback: NetworkCallback<Result>
    ) {
        if (MultiPayUser.walletToken.isNullOrEmpty()) {
            networkManager.sendRequest(
                request = AuthWallet(),
                requestPath = "wallets/list",
                responseModel = Result::class.java,
                networkCallback = networkCallback
            )
        } else {
            networkManager.sendRequest(
                request = Wallet(),
                requestPath = "wallets/list",
                responseModel = Result::class.java,
                networkCallback = networkCallback
            )
        }
    }

    fun addWalletRequest(
        addWalletRequest: AddWalletRequest,
        networkCallback: NetworkCallback<Result>
    ) {
        when (addWalletRequest) {
            is AuthAddWallet -> {
                networkManager.sendRequest(
                    request = addWalletRequest,
                    requestPath = "wallets",
                    responseModel = Result::class.java,
                    networkCallback = networkCallback
                )
            }
            is AddWallet -> {
                networkManager.sendRequest(
                    request = addWalletRequest,
                    requestPath = "wallets",
                    responseModel = Result::class.java,
                    networkCallback = networkCallback
                )
            }
        }
    }

    fun matchWalletRequest(
        walletToken: String,
        networkCallback: NetworkCallback<Result>
    ) {
        if (MultiPayUser.walletToken.isNullOrEmpty()) {
            networkManager.sendRequest(
                request = MatchWallet(walletToken),
                requestPath = "wallets/select",
                responseModel = Result::class.java,
                networkCallback = networkCallback
            )
        } else {
            networkManager.sendRequest(
                request = MatchWallet(walletToken),
                requestPath = "wallets/change",
                responseModel = Result::class.java,
                networkCallback = networkCallback
            )
        }
    }
}