package com.inventiv.multipaysdk

import android.content.Context
import com.inventiv.multipaysdk.data.model.singleton.MultiPayUser
import com.inventiv.multipaysdk.data.model.type.Language
import com.inventiv.multipaysdk.ui.login.LoginActivity
import com.inventiv.multipaysdk.ui.wallet.WalletActivity
import com.inventiv.multipaysdk.util.startActivityWithListener

object MultiPaySdk {

    private lateinit var multiPaySdkComponent: MultiPaySdkComponent

    internal fun getComponent() = multiPaySdkComponent

    @JvmOverloads
    @JvmStatic
    fun init(
        context: Context,
        appToken: String,
        userId: String = String(),
        environment: Environment = Environment.PRODUCTION,
        language: Language? = null
    ) {
        this.multiPaySdkComponent =
            MultiPaySdkComponent(context, appToken, userId, environment, language)
    }

    @JvmStatic
    fun setLanguage(language: Language) = getComponent().setLanguage(language)

    @JvmStatic
    fun startSDKForSubmitConsumer(
        context: Context,
        walletToken: String?,
        listener: MultiPaySdkListener
    ) {
        if (walletToken.isNullOrEmpty()) {
            context.startActivityWithListener(LoginActivity.newIntent(context), listener)
        } else {
            MultiPayUser.walletToken = walletToken
            context.startActivityWithListener(WalletActivity.newIntent(context), listener)
        }
    }
}