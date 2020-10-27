package com.inventiv.multipaysdk.ui.addcard

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.inventiv.multipaysdk.base.BaseContainerActivity

internal class AddCardActivity : BaseContainerActivity() {

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, AddCardActivity::class.java)
    }

    override fun fragment(): Fragment = AddCardFragment.newInstance()
}