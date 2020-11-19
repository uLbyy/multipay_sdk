package com.inventiv.multipaysdk.data.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
internal data class WalletResponse(
    @field:SerializedName("name")
    val name: String?,
    @field:SerializedName("image")
    val imageUrl: String?,
    @field:SerializedName("maskedNumber")
    val maskedNumber: String?,
    @field:SerializedName("token")
    val token: String?,
    @field:SerializedName("balance")
    val balance: String?
) : Parcelable