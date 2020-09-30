package com.inventiv.multipaysdk

import java.io.Serializable

interface MultiPaySdkListener : Serializable {
    fun <T> onSuccess(data: T?)
    fun onFailure(error: String?, code: Int)
    fun onCancelled()
}