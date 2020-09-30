package com.inventiv.multipaysdk.data.model.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
internal data class LoginInfoGsm(
    @field:SerializedName("GSM")
    var gsm: String,
    @field:SerializedName("Password")
    var password: String
) : Parcelable