package com.inventiv.multipaysdk.util

import android.content.Intent
import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

internal fun FragmentActivity.addFragment(fragment: Fragment, frameId: Int) {
    val fragmentTransaction = supportFragmentManager.beginTransaction()
    fragmentTransaction.add(frameId, fragment)
    fragmentTransaction.commit()
}

internal fun Intent.putParcelableExtra(name: String, value: Parcelable) {
    putExtra(name, value)
}