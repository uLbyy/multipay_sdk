package com.inventiv.multipaysdk.data.model.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.inventiv.multipaysdk.MultiPaySdk
import kotlinx.android.parcel.Parcelize

@Parcelize
internal data class AddWallet(
    @field:SerializedName("number")
    var number: String,
    @field:SerializedName("cvv")
    var cvv: String,
    @field:SerializedName("alias")
    var alias: String,
    @field:SerializedName("referenceNumber")
    var referenceNumber: String = MultiPaySdk.getComponent().clientReferenceNo()
) : Parcelable, BaseAuthRequest()