package com.inventiv.multipaysdk.data.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginResponse(
    @field:SerializedName("VerificationCode")
    var verificationCode: String?,
    @field:SerializedName("RemainingTime")
    var remainingTime: String?,
    @field:SerializedName("Gsm")
    var gsm: String?
) : Parcelable