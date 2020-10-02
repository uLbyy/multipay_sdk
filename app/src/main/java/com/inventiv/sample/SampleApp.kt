package com.inventiv.sample

import android.app.Application
import com.inventiv.multipaysdk.Environment
import com.inventiv.multipaysdk.MultiPaySdk
import com.inventiv.multipaysdk.data.model.type.Language

class SampleApp : Application() {

    override fun onCreate() {
        super.onCreate()
        MultiPaySdk.init(
            context = this,
            merchant_token = "784fc69b7543455384a08beeb1d8c3c5",
            environment = Environment.TEST,
            language = Language.TR
        )
    }
}