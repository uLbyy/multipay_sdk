package com.inventiv.multipaysdk.util

object Formatter {

    internal fun servicePhoneNumber(phoneNumber: String) = stringToNumeric(phoneNumber).substring(1)

    private fun stringToNumeric(str: String) = str.replace("[^\\d]".toRegex(), "")
}