package com.inventiv.multipaysdk.data.model.response

import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName
import com.inventiv.multipaysdk.data.model.type.ResultCode

open class BaseResponse(
    @field:SerializedName("Result")
    var result: JsonElement? = null,
    @field:SerializedName("ResultCode")
    var resultCode: Int = 0,
    @field:SerializedName("ResultMessage")
    var resultMessage: String? = null
) {
    open fun isSuccess(): Boolean {
        return resultCode == ResultCode.SUCCESS.value
    }
}