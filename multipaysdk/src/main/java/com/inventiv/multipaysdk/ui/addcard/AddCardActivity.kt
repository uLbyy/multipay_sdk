package com.inventiv.multipaysdk.ui.addcard

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.inventiv.multipaysdk.MultiPaySdkListener
import com.inventiv.multipaysdk.base.BaseContainerActivity

internal class AddCardActivity : BaseContainerActivity() {

    companion object {
        fun newIntent(context: Context, listener: MultiPaySdkListener): Intent {
            return Intent(context, AddCardActivity::class.java).apply {
            }
        }
    }

    override fun fragment(): Fragment = AddCardFragment.newInstance()
}