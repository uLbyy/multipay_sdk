package com.inventiv.multipaysdk.ui.addcard

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.inventiv.multipaysdk.MultiPaySdkListener
import com.inventiv.multipaysdk.base.BaseContainerActivity
import com.inventiv.multipaysdk.util.KEY_MULTIPAY_SDK_LISTENER

internal class AddCardActivity : BaseContainerActivity() {

    private lateinit var listener: MultiPaySdkListener

    override fun onCreate(savedInstanceState: Bundle?) {
        listener = intent.getSerializableExtra(KEY_MULTIPAY_SDK_LISTENER) as MultiPaySdkListener
        super.onCreate(savedInstanceState)
    }

    override fun fragment(): Fragment = AddCardFragment.newInstance(listener)
}