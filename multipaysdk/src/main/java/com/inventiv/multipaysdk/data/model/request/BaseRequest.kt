package com.inventiv.multipaysdk.data.model.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.inventiv.multipaysdk.MultiPaySdk
import kotlinx.android.parcel.Parcelize

@Parcelize
internal open class BaseRequest(
    @field:SerializedName("languageCode")
    var languageCode: String = MultiPaySdk.getComponent().language().code
) : Parcelable {
    override fun toString(): String {
        return "BaseRequest(languageCode='$languageCode')"
    }
}