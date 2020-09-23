package com.inventiv.sample

import android.app.Application
import com.inventiv.multipaysdk.MultiPay
import com.inventiv.multipaysdk.data.model.type.Language

class SampleApp : Application() {

    override fun onCreate() {
        super.onCreate()
        MultiPay.init(this,Language.TR)
    }
}