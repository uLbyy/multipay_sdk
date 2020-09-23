package com.inventiv.multipaysdk.data.model.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.inventiv.multipaysdk.MultiPay
import kotlinx.android.parcel.Parcelize

@Parcelize
open class BaseRequest(
    @field:SerializedName("AppToken")
    var appToken: String = "784fc69b7543455384a08beeb1d8c3c5",
    @field:SerializedName("LanguageCode")
    var languageCode: String = MultiPay.language().code
) : Parcelable