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
            appToken = "40dd196a092c4abe8c16f26c1c521b7c",
            environment = Environment.TEST,
            language = Language.TR
        )
    }
}