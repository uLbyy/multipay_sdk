package com.inventiv.multipaysdk.data.model.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
internal data class ConfirmOtp(
    @field:SerializedName("verificationCode")
    var verificationCode: String?,
    @field:SerializedName("smsCode")
    var smsCode: String
) : Parcelable, BaseRequest()