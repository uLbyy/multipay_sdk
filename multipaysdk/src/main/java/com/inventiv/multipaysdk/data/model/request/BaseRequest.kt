package com.inventiv.multipaysdk.data.model.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.inventiv.multipaysdk.MultiPaySdk
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
internal open class BaseRequest(
    @field:SerializedName("AppToken")
    var appToken: String = MultiPaySdk.getComponent().merchantToken(),
    @field:SerializedName("LanguageCode")
    var languageCode: String = MultiPaySdk.getComponent().language().code,
    @field:SerializedName("requestId")
    var requestId: String? = UUID.randomUUID().toString()
) : Parcelable {
    override fun toString(): String {
        return "BaseRequest(appToken='$appToken', languageCode='$languageCode', requestId=$requestId)"
    }
}