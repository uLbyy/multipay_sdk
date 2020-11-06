package com.inventiv.multipaysdk.data.api.error

import com.inventiv.multipaysdk.data.model.response.Result

internal class ApiError : Exception {

    var errorCode = 0
        private set
    var statusCode = 0
        private set
    var data: ByteArray? = null
        private set
    var result: Result? = null
        private set

    private constructor(detailMessage: String) : super(detailMessage)

    private constructor(detailMessage: String, throwable: Throwable) : super(
        detailMessage,
        throwable
    )

    private constructor(throwable: Throwable) : super(throwable)

    companion object {

        const val ERROR_GENERAL = 0
        const val ERROR_NETWORK = 1
        const val ERROR_NO_NETWORK_CONNECTION = 2
        const val ERROR_INVALID_RESPONSE = 3
        const val ERROR_SERVER = 4

        const val GENERAL_ERROR_MESSAGE = "An error occurred."
        const val NETWORK_ERROR_MESSAGE = "A Network error occurred."
        const val NO_NETWORK_ERROR_MESSAGE = "No network connection."
        const val INVALID_RESPONSE_MESSAGE = "A non-jsonObject response received from server."

        @JvmOverloads
        fun generalInstance(errorMessage: String = GENERAL_ERROR_MESSAGE): ApiError {
            val error = ApiError(errorMessage)
            error.errorCode = ERROR_GENERAL
            return error
        }

        @JvmOverloads
        fun networkErrorInstance(errorMessage: String = NETWORK_ERROR_MESSAGE): ApiError {
            val error = ApiError(errorMessage)
            error.errorCode = ERROR_NETWORK
            return error
        }

        @JvmOverloads
        fun noConnectionInstance(errorMessage: String = NO_NETWORK_ERROR_MESSAGE): ApiError {
            val error = ApiError(errorMessage)
            error.errorCode = ERROR_NO_NETWORK_CONNECTION
            return error
        }

        @JvmOverloads
        fun invalidResponseInstance(errorMessage: String = INVALID_RESPONSE_MESSAGE): ApiError {
            val error = ApiError(errorMessage)
            error.errorCode = ERROR_INVALID_RESPONSE
            return error
        }

        @JvmOverloads
        fun serverErrorInstance(
            errorMessage: String,
            statusCode: Int,
            data: ByteArray?
        ): ApiError {
            val error = ApiError(errorMessage)
            error.errorCode = ERROR_SERVER
            error.statusCode = statusCode
            error.data = data
            return error
        }

        @JvmOverloads
        fun apiErrorInstance(
            errorMessage: String?,
            statusCode: Int,
            result: Result?
        ): ApiError {
            val error = ApiError(errorMessage ?: GENERAL_ERROR_MESSAGE)
            error.errorCode = ERROR_SERVER
            error.statusCode = statusCode
            error.result = result
            return error
        }
    }

    override fun toString(): String {
        return "ApiError(errorCode=$errorCode, " +
                "statusCode=$statusCode, " +
                "data=" + (if (data != null) String(data!!) else "") + ", " +
                "cause=" + (cause?.toString() ?: "") + ", " +
                "message=" + message + ')'
    }
}
