package com.inventiv.sample

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.inventiv.multipaysdk.MultiPaySdk
import com.inventiv.multipaysdk.MultiPaySdkListener
import com.inventiv.multipaysdk.data.model.type.Language

class MainActivity : AppCompatActivity() {

    private lateinit var buttonTest: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonTest = findViewById(R.id.btn_test_env)
        buttonTest.setOnClickListener {
            MultiPaySdk.setLanguage(Language.EN)
            MultiPaySdk.startSDKForSubmitConsumer(this, object : MultiPaySdkListener {
                override fun <T> onSuccess(data: T?) {
                    Log.d("MainActivity", "onSuccess: data = $data")
                }

                override fun onFailure(error: String?, code: Int) {
                    Log.d("MainActivity", "onFailure: error = $error code = $code")
                }

                override fun onCancelled() {
                    Log.d("MainActivity", "onCancelled")
                }
            })
        }
    }
}