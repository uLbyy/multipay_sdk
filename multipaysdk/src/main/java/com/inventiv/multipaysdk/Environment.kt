package com.inventiv.multipaysdk

enum class Environment(
    internal val baseUrl: String,
    internal val apiServicePath: String,
    internal val loginBaseUrl: String,
    internal val loginApiServicePath: String
) {
    TEST(
        "https://test-multinet-enterpriseservices-rest.inventiv.services",
        "/MultinetCardService",
        "https://test-multinet-multipay-api.inventiv.services",
        "/MultiUService"
    ),
    PRODUCTION(
        "https://webservices.multinet.com.tr",
        "/rest/MultinetCardService",
        "",
        ""
    )
}