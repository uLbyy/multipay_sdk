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
    private var appToken: String,
    private var clientReferenceNo: String,
    private val environment: Environment,
    private var language: Language?
) {
    private val volleyManager: VolleyManager
    private val networkManager: NetworkManager
    private val apiService: ApiService
    private var activityCountOnStack = 0

    init {
        this.language = getLanguage(appContext, language)
        volleyManager = VolleyManager(appContext)
        networkManager = NetworkManager(volleyManager, environment)
        apiService = ApiService(networkManager)
        Logger.logging(BuildConfig.DEBUG)
    }

    fun environment() = environment

    fun appToken() = appToken

    fun setAppToken(appToken: String) {
        this.appToken = appToken
    }

    fun clientReferenceNo() = clientReferenceNo

    fun setClientReferenceNo(clientReferenceNo: String) {
        this.clientReferenceNo = clientReferenceNo
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

    fun activityCountOnStack() = activityCountOnStack

    fun activityCreated(): Int {
        synchronized(this) {
            activityCountOnStack += 1
            return activityCountOnStack
        }
    }

    fun activityDestroyed(): Int {
        synchronized(this) {
            if (activityCountOnStack > 0) {
                activityCountOnStack -= 1
            } else {
                activityCountOnStack = 0
            }
            return activityCountOnStack
        }
    }
}