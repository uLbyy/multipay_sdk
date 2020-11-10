package com.inventiv.multipaysdk.data.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
internal data class WalletResponse(
    @field:SerializedName("productId")
    val productId: Int,
    @field:SerializedName("productName")
    val productName: String?,
    @field:SerializedName("alias")
    val alias: String?,
    @field:SerializedName("maskedNumber")
    val maskedNumber: String?,
    @field:SerializedName("token")
    val token: String?,
    @field:SerializedName("balance")
    val balance: String?
) : Parcelable