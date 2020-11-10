package com.inventiv.multipaysdk.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.inventiv.multipaysdk.MultiPaySdk
import com.inventiv.multipaysdk.data.api.ApiService
import com.inventiv.multipaysdk.data.api.callback.NetworkCallback
import com.inventiv.multipaysdk.data.api.error.ApiError
import com.inventiv.multipaysdk.data.model.Event
import com.inventiv.multipaysdk.data.model.Resource
import com.inventiv.multipaysdk.data.model.request.AddWallet
import com.inventiv.multipaysdk.data.model.request.Wallet
import com.inventiv.multipaysdk.data.model.response.AddWalletResponse
import com.inventiv.multipaysdk.data.model.response.Result
import com.inventiv.multipaysdk.data.model.response.WalletsResponse

internal class WalletRepository(private val apiService: ApiService) {

    private val addWalletResult =
        MediatorLiveData<Event<Resource<AddWalletResponse>>>()

    private val walletsResult =
        MediatorLiveData<Event<Resource<WalletsResponse>>>()

    fun addWallet(addWallet: AddWallet): LiveData<Event<Resource<AddWalletResponse>>> {

        addWalletResult.postValue(Event(Resource.Loading()))

        apiService.addWalletRequest(addWallet, object : NetworkCallback<Result> {
            override fun onSuccess(response: Result?) {
                val gson = MultiPaySdk.getComponent().gson()
                val addWalletResponse = gson.fromJson<AddWalletResponse>(
                    response?.result,
                    AddWallet::class.java
                )
                addWalletResult.postValue(Event(Resource.Success(addWalletResponse)))
            }

            override fun onError(error: ApiError) {
                addWalletResult.postValue(Event(Resource.Failure(error.message)))
            }
        })

        return addWalletResult
    }

    fun wallets(): LiveData<Event<Resource<WalletsResponse>>> {

        walletsResult.postValue(Event(Resource.Loading()))

        apiService.walletRequest(Wallet(), object : NetworkCallback<Result> {
            override fun onSuccess(response: Result?) {
                val gson = MultiPaySdk.getComponent().gson()
                val walletsResponse = gson.fromJson<WalletsResponse>(
                    response?.result,
                    WalletsResponse::class.java
                )
                walletsResult.postValue(Event(Resource.Success(walletsResponse)))
            }

            override fun onError(error: ApiError) {
                walletsResult.postValue(Event(Resource.Failure(error.message)))
            }
        })

        return walletsResult
    }
}