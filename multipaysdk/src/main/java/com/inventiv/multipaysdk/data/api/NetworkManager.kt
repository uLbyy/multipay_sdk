package com.inventiv.multipaysdk.data.api

import com.inventiv.multipaysdk.data.api.callback.NetworkCallback
import com.inventiv.multipaysdk.data.api.error.ApiError
import com.inventiv.multipaysdk.data.model.request.BaseRequest
import com.inventiv.multipaysdk.data.model.response.BaseResponse
import com.inventiv.multipaysdk.data.model.response.Result

internal class NetworkManager(private val volleyManager: VolleyManager) {

    companion object {

        private const val TAG = "NetworkManager"

        private const val baseUrl = "https://test-multinet-multipay-api.inventiv.services"
        const val apiServicePath = "/MultiUService"

        const val DEFAULT_TIMEOUT_MILLIS = 60000

        const val PRIORITY_LOW = 0
        const val PRIORITY_NORMAL = 1
        const val PRIORITY_HIGH = 2
        const val PRIORITY_IMMEDIATE = 3

        private const val HEADER_KEY_OS_VERSION = "device-os-version"

        private const val HEADER_VALUE_OS_VERSION = "Android"
    }

    fun volleyManager() = volleyManager

    fun <T : BaseResponse> sendRequest(
        request: BaseRequest,
        requestPath: String,
        responseModel: Class<T>,
        networkCallback: NetworkCallback<T>
    ) {
        val headers = mutableMapOf<String, String>()
        headers[HEADER_KEY_OS_VERSION] = HEADER_VALUE_OS_VERSION

        volleyManager.sendRequest(
            "$baseUrl$apiServicePath",
            request,
            requestPath,
            headers,
            responseModel,
            object : NetworkCallback<T> {
                override fun onSuccess(response: T?) {
                    handleNetworkResponse(response, null, networkCallback)
                }

                override fun onError(volleyError: ApiError) {
                    handleNetworkResponse(null, volleyError, networkCallback)
                }
            }
        )
    }

    private fun <T : BaseResponse> handleNetworkResponse(
        response: T?,
        volleyError: ApiError?,
        networkCallback: NetworkCallback<T>
    ) {
        volleyError?.let {
            deliverResponse(null, volleyError, networkCallback)
            return
        }

        if (response !is Result) {
            deliverResponse(response, null, networkCallback)
            return
        }

        if (response.isSuccess()) {
            deliverResponse(response, null, networkCallback)
        } else {
            val apiError =
                ApiError.apiErrorInstance(response.resultMessage, response.resultCode, response)
            deliverResponse(null, apiError, networkCallback)
        }
    }

    private fun <T : BaseResponse> deliverResponse(
        response: T?,
        apiError: ApiError?,
        callback: NetworkCallback<T>
    ) {
        apiError?.let {
            callback.onError(it)
            return
        }
        callback.onSuccess(response)
    }
}
