package com.inventiv.multipaysdk.data.model.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
internal data class ConfirmOtp(
    @field:SerializedName("VerificationCode")
    var verificationCode: String?,
    @field:SerializedName("otpCode")
    var otpCode: String
) : Parcelable, BaseRequest() {
}