package com.inventiv.multipaysdk.data.model.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
internal data class CreateMultinetCard(
    @field:SerializedName("cardNumber")
    var cardNumber: String,
    @field:SerializedName("cvv")
    var cvv: String,
    @field:SerializedName("cardAlias")
    var cardAlias: String,
    @field:SerializedName("phoneNumber")
    var phoneNumber: String,
    @field:SerializedName("clientReferenceNo")
    var clientReferenceNo: String
) : Parcelable, BaseRequest()