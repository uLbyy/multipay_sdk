package com.inventiv.multipaysdk.ui.login

import androidx.fragment.app.Fragment
import com.inventiv.multipaysdk.base.BaseContainerActivity

class LoginActivity : BaseContainerActivity() {

    override fun fragment(): Fragment = LoginFragment.newInstance()
}