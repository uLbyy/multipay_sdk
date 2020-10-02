package com.inventiv.multipaysdk

import android.content.Context
import android.content.Intent
import com.inventiv.multipaysdk.data.model.type.Language
import com.inventiv.multipaysdk.ui.login.LoginActivity
import com.inventiv.multipaysdk.util.KEY_MULTIPAY_SDK_LISTENER

object MultiPaySdk {

    private lateinit var multiPaySdkComponent: MultiPaySdkComponent

    internal fun getComponent() = multiPaySdkComponent

    @JvmOverloads
    @JvmStatic
    fun init(
        context: Context,
        merchant_token: String,
        environment: Environment = Environment.PRODUCTION,
        language: Language? = null
    ) {
        this.multiPaySdkComponent =
            MultiPaySdkComponent(context, merchant_token, environment, language)
    }

    @JvmStatic
    fun setLanguage(language: Language) = getComponent().setLanguage(language)

    @JvmStatic
    fun startSDKForSubmitConsumer(context: Context, listener: MultiPaySdkListener) {
        val intent = Intent(context, LoginActivity::class.java)
        intent.putExtra(KEY_MULTIPAY_SDK_LISTENER, listener)
        context.startActivity(intent)
    }
}