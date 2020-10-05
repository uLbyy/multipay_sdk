package com.inventiv.multipaysdk.ui.addcard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.inventiv.multipaysdk.MultiPaySdkListener
import com.inventiv.multipaysdk.base.BaseContainerActivity
import com.inventiv.multipaysdk.util.KEY_MULTIPAY_SDK_LISTENER

internal class AddCardActivity : BaseContainerActivity() {

    companion object {
        fun newIntent(context: Context, listener: MultiPaySdkListener): Intent {
            return Intent(context, AddCardActivity::class.java).apply {
                putExtra(KEY_MULTIPAY_SDK_LISTENER, listener)
            }
        }
    }

    private lateinit var listener: MultiPaySdkListener

    override fun onCreate(savedInstanceState: Bundle?) {
        listener = intent.getSerializableExtra(KEY_MULTIPAY_SDK_LISTENER) as MultiPaySdkListener
        super.onCreate(savedInstanceState)
    }

    override fun fragment(): Fragment = AddCardFragment.newInstance(listener)
}