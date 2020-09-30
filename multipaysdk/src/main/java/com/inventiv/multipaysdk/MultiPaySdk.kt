package com.inventiv.multipaysdk

import android.content.Context
import android.content.Intent
import com.inventiv.multipaysdk.data.model.type.Language
import com.inventiv.multipaysdk.ui.login.LoginActivity
import com.inventiv.multipaysdk.util.KEY_MULTIPAY_SDK_LISTENER

object MultiPaySdk {

    private lateinit var multiPaySdkComponent: MultiPaySdkComponent

    @JvmOverloads
    @JvmStatic
    fun init(context: Context, language: Language? = null) {
        this.multiPaySdkComponent = MultiPaySdkComponent(context, language)
    }

    @JvmStatic
    internal fun getComponent() = multiPaySdkComponent

    @JvmStatic
    fun startSDKForSubmitConsumer(context: Context, listener: MultiPaySdkListener) {
        val intent = Intent(context, LoginActivity::class.java)
        intent.putExtra(KEY_MULTIPAY_SDK_LISTENER, listener)
        context.startActivity(intent)
    }
}