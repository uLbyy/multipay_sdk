package com.inventiv.multipaysdk.data.model.singleton

import com.inventiv.multipaysdk.data.model.response.ConfirmOtpResponse

internal object MultiPayUser {
    var confirmOtpResponse: ConfirmOtpResponse? = null
    val clientReferenceNo: String = "526de1e333d64b67a05d7067e38fced9"
    val appTokenAfterLogin: String = "40dd196a092c4abe8c16f26c1c521b7c"
}