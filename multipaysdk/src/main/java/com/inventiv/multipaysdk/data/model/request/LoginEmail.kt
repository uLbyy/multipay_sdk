package com.inventiv.multipaysdk.data.model.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
internal data class LoginEmail(
    @field:SerializedName("email")
    var email: String,
    @field:SerializedName("password")
    var password: String
) : LoginRequest(), Parcelable