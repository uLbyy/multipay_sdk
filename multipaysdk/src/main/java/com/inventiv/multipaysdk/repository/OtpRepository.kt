package com.inventiv.multipaysdk.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.inventiv.multipaysdk.MultiPaySdk
import com.inventiv.multipaysdk.data.api.ApiService
import com.inventiv.multipaysdk.data.api.callback.NetworkCallback
import com.inventiv.multipaysdk.data.api.error.ApiError
import com.inventiv.multipaysdk.data.model.Event
import com.inventiv.multipaysdk.data.model.Resource
import com.inventiv.multipaysdk.data.model.request.ConfirmOtp
import com.inventiv.multipaysdk.data.model.request.ResendOtp
import com.inventiv.multipaysdk.data.model.response.ConfirmOtpResponse
import com.inventiv.multipaysdk.data.model.response.ResendOtpResponse
import com.inventiv.multipaysdk.data.model.response.Result
import com.inventiv.multipaysdk.data.model.singleton.MultiPayUser

internal class OtpRepository(private val apiService: ApiService) {

    private val confirmOtpResult = MediatorLiveData<Event<Resource<ConfirmOtpResponse>>>()
    private val resendOtpResult = MediatorLiveData<Event<Resource<ResendOtpResponse>>>()

    fun confirmOtp(
        confirmOtp: ConfirmOtp
    ): LiveData<Event<Resource<ConfirmOtpResponse>>> {

        confirmOtpResult.postValue(Event(Resource.Loading()))

        apiService.confirmOtpRequest(confirmOtp, object : NetworkCallback<Result> {
            override fun onSuccess(response: Result?) {
                val gson = MultiPaySdk.getComponent().gson()
                val confirmOtpResponse = gson.fromJson<ConfirmOtpResponse>(
                    response?.result,
                    ConfirmOtpResponse::class.java
                )
                MultiPayUser.confirmOtpResponse = confirmOtpResponse
                MultiPaySdk.getComponent().setMerchantToken(MultiPayUser.appTokenAfterLogin)
                confirmOtpResult.postValue(Event(Resource.Success(confirmOtpResponse)))
            }

            override fun onError(error: ApiError) {
                confirmOtpResult.postValue(Event(Resource.Failure(error.message)))
            }
        })
        return confirmOtpResult
    }

    fun resendOtp(
        resendOtp: ResendOtp
    ): LiveData<Event<Resource<ResendOtpResponse>>> {

        resendOtpResult.postValue(Event(Resource.Loading()))

        apiService.resendOtpRequest(resendOtp, object : NetworkCallback<Result> {
            override fun onSuccess(response: Result?) {
                val gson = MultiPaySdk.getComponent().gson()
                val resendOtpResponse = gson.fromJson<ResendOtpResponse>(
                    response?.result,
                    ResendOtpResponse::class.java
                )
                resendOtpResult.postValue(Event(Resource.Success(resendOtpResponse)))
            }

            override fun onError(error: ApiError) {
                resendOtpResult.postValue(Event(Resource.Failure(error.message)))
            }
        })
        return resendOtpResult
    }
}