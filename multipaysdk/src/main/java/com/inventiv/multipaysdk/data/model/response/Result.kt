package com.inventiv.multipaysdk.data.model.response

import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName
import com.inventiv.multipaysdk.data.model.type.ResultCode

internal data class Result(
    @field:SerializedName("Result")
    var result: JsonElement? = null,
    @field:SerializedName("ResultCode")
    var resultCode: Int = 0,
    @field:SerializedName("ResultMessage")
    var resultMessage: String? = null
) : BaseResponse {
    fun isSuccess(): Boolean {
        return resultCode == ResultCode.SUCCESS.value
    }
}