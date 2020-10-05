package com.inventiv.multipaysdk.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.inventiv.multipaysdk.MultiPaySdk
import com.inventiv.multipaysdk.data.api.ApiService
import com.inventiv.multipaysdk.data.api.callback.NetworkCallback
import com.inventiv.multipaysdk.data.api.error.ApiError
import com.inventiv.multipaysdk.data.model.Event
import com.inventiv.multipaysdk.data.model.Resource
import com.inventiv.multipaysdk.data.model.request.CreateMultinetCard
import com.inventiv.multipaysdk.data.model.response.CreateMultinetCardResponse
import com.inventiv.multipaysdk.data.model.response.Result

internal class CardRepository(private val apiService: ApiService) {

    private val createMultinetResult =
        MediatorLiveData<Event<Resource<CreateMultinetCardResponse>>>()

    fun createMultinet(createMultinetCard: CreateMultinetCard): LiveData<Event<Resource<CreateMultinetCardResponse>>> {

        createMultinetResult.postValue(Event(Resource.Loading()))

        apiService.createMultinetCardRequest(createMultinetCard, object : NetworkCallback<Result> {
            override fun onSuccess(response: Result?) {
                val gson = MultiPaySdk.getComponent().gson()
                val createMultinetCardResponse = gson.fromJson<CreateMultinetCardResponse>(
                    response?.result,
                    CreateMultinetCard::class.java
                )
                createMultinetResult.postValue(Event(Resource.Success(createMultinetCardResponse)))
            }

            override fun onError(error: ApiError) {
                createMultinetResult.postValue(Event(Resource.Failure(error.message)))
            }
        })

        return createMultinetResult
    }
}