package com.inventiv.multipaysdk.util

internal object Formatter {

    fun servicePhoneNumber(phoneNumber: String) = stringToNumeric(phoneNumber).substring(1)

    private fun stringToNumeric(str: String) = str.replace("[^\\d]".toRegex(), "")
}