package com.inventiv.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.inventiv.multipaysdk.MultiPaySdk
import com.inventiv.multipaysdk.MultiPaySdkListener
import com.inventiv.multipaysdk.data.model.type.Language

class MainActivity : AppCompatActivity(), MultiPaySdkListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MultiPaySdk.setLanguage(Language.EN)
        MultiPaySdk.startSDKForSubmitConsumer(this, this)
    }

    override fun <T> onSuccess(data: T?) {
        // Toast.makeText(this, "onSuccess", Toast.LENGTH_LONG).show()
    }

    override fun onFailure(error: String?, code: Int) {
        //Toast.makeText(this, "onFailure", Toast.LENGTH_LONG).show()
    }

    override fun onCancelled() {
        // Toast.makeText(this, "onCancelled", Toast.LENGTH_LONG).show()
    }
}