package com.inventiv.multipaysdk

import android.content.Context
import androidx.annotation.StringRes
import com.google.gson.Gson
import com.inventiv.multipaysdk.data.api.ApiService
import com.inventiv.multipaysdk.data.api.NetworkManager
import com.inventiv.multipaysdk.data.api.VolleyManager
import com.inventiv.multipaysdk.data.model.type.Language
import com.inventiv.multipaysdk.util.Logger
import com.inventiv.multipaysdk.util.getLanguage

internal class MultiPaySdkComponent(
    private val appContext: Context,
    private var merchantToken: String,
    private val environment: Environment,
    private var language: Language?
) {
    private val volleyManager: VolleyManager
    private val networkManager: NetworkManager
    private val apiService: ApiService

    init {
        this.language = getLanguage(appContext, language)
        volleyManager = VolleyManager(appContext)
        networkManager = NetworkManager(volleyManager, environment)
        apiService = ApiService(networkManager)
        Logger.logging(BuildConfig.DEBUG)
    }

    fun environment() = environment

    fun merchantToken() = merchantToken

    fun setMerchantToken(merchantToken: String) {
        this.merchantToken = merchantToken
    }

    fun apiService() = apiService

    fun language() = requireNotNull(language)

    fun requireAppContext() = appContext

    fun setLanguage(language: Language?) {
        this.language = getLanguage(appContext, language)
    }

    fun gson() = Gson()

    fun getString(@StringRes resId: Int, vararg args: Any): String {
        return appContext.getString(resId, args)
    }

    fun volleyManager() = volleyManager
}