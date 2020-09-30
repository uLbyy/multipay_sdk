package com.inventiv.sample

import android.app.Application
import com.inventiv.multipaysdk.MultiPaySdk
import com.inventiv.multipaysdk.data.model.type.Language

class SampleApp : Application() {

    override fun onCreate() {
        super.onCreate()
        MultiPaySdk.init(this, Language.TR)
    }
}