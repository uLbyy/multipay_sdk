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

    override fun toString(): String {
        return "ApiError{errorCode=$errorCode, statusCode=$statusCode, data=" + (if (data != null) String(
            data!!
        ) else "") + ", cause=" + (cause?.toString() ?: "") + ", message=" + message + '}'
    }

    companion object {

        const val ERROR_CODE_CANCEL = -2
        const val ERROR_CODE_NO_CONNECTION = -1
        const val ERROR_GENERAL = 0
        const val ERROR_SERVER = 3
        const val ERROR_SERIALIZATION_FAILED = 9998
        const val ERROR_API_KEY_DOES_NOT_EXISTS = 9997
        const val ERROR_INBOX_DOES_NOT_HAVE_NEXT_PAGE = 9996
        const val ERROR_INVALID_PARAMETERS = 9995
        const val ERROR_INVALID_RESPONSE = 9994
        const val GENERAL_ERROR_MESSAGE = "An error occurred."

        fun noApiKeyInstance(): ApiError {
            val error =
                ApiError("There is no API Key that is set. You should give your API key to SDK via PayTest.setApiKey method.")
            error.errorCode = ERROR_API_KEY_DOES_NOT_EXISTS
            return error
        }

        fun responseSerializationFailedInstance(throwable: Throwable): ApiError {
            val error = ApiError("Error during converting response to class.", throwable)
            error.errorCode = ERROR_SERIALIZATION_FAILED
            return error
        }

        fun invalidResponseInstance(): ApiError {
            val error = ApiError("A non-jsonObject response received from server.")
            error.errorCode = ERROR_INVALID_RESPONSE
            return error
        }

        fun requestSerializationFailedInstance(throwable: Throwable): ApiError {
            val error = ApiError("Error during converting request to jsonObject.", throwable)
            error.errorCode = ERROR_SERIALIZATION_FAILED
            return error
        }

        fun inboxDoesNotHaveNextPageInstance(): ApiError {
            val error =
                ApiError("Inbox does not have more pages. You can check this via hasNextPage property.")
            error.errorCode = ERROR_INBOX_DOES_NOT_HAVE_NEXT_PAGE
            return error
        }

        fun invalidParametersInstance(): ApiError {
            val error = ApiError("Given parameters are invalid.")
            error.errorCode = ERROR_INVALID_PARAMETERS
            return error
        }

        fun noConnectionInstance(): ApiError {
            val error = ApiError("No network connection.")
            error.errorCode = ERROR_CODE_NO_CONNECTION
            return error
        }

        @JvmOverloads
        fun generalInstance(errorMessage: String = GENERAL_ERROR_MESSAGE): ApiError {
            val error = ApiError(errorMessage)
            error.errorCode = ERROR_GENERAL
            return error
        }

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

        fun cancelInstance(): ApiError {
            val error = ApiError("Request has been cancelled.")
            error.errorCode = ERROR_CODE_CANCEL
            return error
        }

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
}
