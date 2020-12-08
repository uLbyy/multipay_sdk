package com.inventiv.multipaysdk.data.model.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
internal class MatchWallet(
    @field:SerializedName("walletToken")
    var walletToken: String
) : BaseRequest(), Parcelable