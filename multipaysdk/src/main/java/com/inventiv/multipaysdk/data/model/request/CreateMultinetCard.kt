package com.inventiv.multipaysdk.data.model.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
internal data class CreateMultinetCard(
    @field:SerializedName("number")
    var number: String,
    @field:SerializedName("cvv")
    var cvv: String,
    @field:SerializedName("alias")
    var alias: String
) : Parcelable, BaseAuthRequest()