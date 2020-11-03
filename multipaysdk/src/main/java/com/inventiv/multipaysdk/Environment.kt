package com.inventiv.multipaysdk

enum class Environment(
    internal val baseUrl: String,
    internal val apiServicePath: String
) {
    TEST(
        "http://192.168.0.31:44562/",
        "multipay-sdk/v1/"

    ),
    PRODUCTION(
        "http://192.168.0.31:44562/",
        "multipay-sdk/v1/"
    )
}