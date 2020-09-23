package com.inventiv.multipaysdk.base

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.inventiv.multipaysdk.MultiPay
import com.inventiv.multipaysdk.R
import com.inventiv.multipaysdk.util.addFragment
import com.inventiv.multipaysdk.util.updateBaseContextLocale

abstract class BaseContainerActivity : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context?) {
        if (newBase == null) {
            super.attachBaseContext(newBase)
        } else {
            super.attachBaseContext(updateBaseContextLocale(newBase, MultiPay.language().id))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common)
        val fragment = supportFragmentManager.findFragmentById(R.id.layout_container)
        if (fragment == null) {
            addFragment(fragment(), R.id.layout_container)
        }
    }

    protected abstract fun fragment(): Fragment
}