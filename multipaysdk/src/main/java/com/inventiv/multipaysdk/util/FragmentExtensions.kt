package com.inventiv.multipaysdk.util

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.inventiv.multipaysdk.data.api.error.ApiError

internal fun Fragment.showSnackBarAlert(message: String? = ApiError.GENERAL_ERROR_MESSAGE) {
    val snackbar: Snackbar = Snackbar.make(
        requireView(),
        message ?: ApiError.GENERAL_ERROR_MESSAGE,
        Snackbar.LENGTH_LONG
    )
    SnackBarUtil.alert(snackbar).show()
}

internal fun Fragment.showSnackBarInfo(message: String? = ApiError.GENERAL_ERROR_MESSAGE) {
    val snackbar: Snackbar = Snackbar.make(
        requireView(),
        message ?: ApiError.GENERAL_ERROR_MESSAGE,
        Snackbar.LENGTH_LONG
    )
    SnackBarUtil.info(snackbar).show()
}

internal fun Fragment.showSnackBarConfirm(message: String? = ApiError.GENERAL_ERROR_MESSAGE) {
    val snackbar: Snackbar = Snackbar.make(
        requireView(),
        message ?: ApiError.GENERAL_ERROR_MESSAGE,
        Snackbar.LENGTH_LONG
    )
    SnackBarUtil.confirm(snackbar).show()
}