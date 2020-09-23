package com.inventiv.multipaysdk.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

internal fun FragmentActivity.addFragment(fragment: Fragment, frameId: Int) {
    val fragmentTransaction = supportFragmentManager.beginTransaction()
    fragmentTransaction.add(frameId, fragment)
    fragmentTransaction.commit()
}