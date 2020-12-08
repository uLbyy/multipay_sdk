package com.inventiv.multipaysdk.data.model.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.inventiv.multipaysdk.MultiPaySdk
import kotlinx.android.parcel.Parcelize

@Parcelize
internal class Wallet(
    @field:SerializedName("referenceNumber")
    var referenceNumber: String? = MultiPaySdk.getComponent().clientReferenceNo()
) : BaseWalletTokenRequest(), Parcelable