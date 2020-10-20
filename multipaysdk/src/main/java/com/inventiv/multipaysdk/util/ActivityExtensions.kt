package com.inventiv.multipaysdk.util

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.inventiv.multipaysdk.MultiPaySdkListener

internal fun FragmentActivity.addFragment(fragment: Fragment, frameId: Int) {
    val fragmentTransaction = supportFragmentManager.beginTransaction()
    fragmentTransaction.add(frameId, fragment, fragment.javaClass.simpleName)
    fragmentTransaction.commit()
}

internal fun Intent.putParcelableExtra(name: String, value: Parcelable) {
    putExtra(name, value)
}

internal fun FragmentActivity.startActivityWithListener(
    intent: Intent,
    listener: MultiPaySdkListener
) {
    intent.putExtra(KEY_MULTIPAY_SDK_LISTENER, listener)
    startActivity(intent)
}

internal fun Context.startActivityWithListener(intent: Intent, listener: MultiPaySdkListener) {
    intent.putExtra(KEY_MULTIPAY_SDK_LISTENER, listener)
    startActivity(intent)
}