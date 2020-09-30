package com.inventiv.multipaysdk

import android.content.Context
import com.google.gson.Gson
import com.inventiv.multipaysdk.data.api.ApiService
import com.inventiv.multipaysdk.data.api.NetworkManager
import com.inventiv.multipaysdk.data.api.VolleyManager
import com.inventiv.multipaysdk.data.model.type.Language
import com.inventiv.multipaysdk.util.getLanguage

internal class MultiPaySdkComponent(
    private val appContext: Context,
    private var language: Language?
) {
    private val volleyManager: VolleyManager
    private val networkManager: NetworkManager
    private val apiService: ApiService

    init {
        this.language = getLanguage(appContext, language)
        volleyManager = VolleyManager(appContext)
        networkManager = NetworkManager(volleyManager)
        apiService = ApiService(networkManager)
    }

    fun apiService() = apiService

    fun language() = requireNotNull(language)

    fun requireAppContext() = appContext

    fun setLanguage(language: Language?) {
        this.language = getLanguage(appContext, language)
    }

    fun gson() = Gson()
}