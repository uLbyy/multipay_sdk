package com.inventiv.multipaysdk.ui.otp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.inventiv.multipaysdk.MultiPaySdkListener
import com.inventiv.multipaysdk.base.BaseContainerActivity
import com.inventiv.multipaysdk.data.model.type.OtpDirectionFrom
import com.inventiv.multipaysdk.util.EXTRA_OTP_DIRECTION_FROM
import com.inventiv.multipaysdk.util.EXTRA_OTP_NAVIGATION
import com.inventiv.multipaysdk.util.KEY_MULTIPAY_SDK_LISTENER
import com.inventiv.multipaysdk.util.putParcelableExtra

internal class OtpActivity : BaseContainerActivity() {

    companion object {
        fun newIntent(
            context: Context,
            otpNavigationArgs: OtpNavigationArgs,
            otpDirectionFrom: OtpDirectionFrom,
            listener: MultiPaySdkListener
        ): Intent {
            return Intent(context, OtpActivity::class.java).apply {
                putExtra(EXTRA_OTP_NAVIGATION, otpNavigationArgs)
                putParcelableExtra(EXTRA_OTP_DIRECTION_FROM, otpDirectionFrom)
                putExtra(KEY_MULTIPAY_SDK_LISTENER, listener)
            }
        }
    }

    private var otpNavigationArgs: OtpNavigationArgs? = null
    private var otpDirectionFrom: OtpDirectionFrom? = null

    private lateinit var listener: MultiPaySdkListener

    override fun onCreate(savedInstanceState: Bundle?) {
        listener = intent.getSerializableExtra(KEY_MULTIPAY_SDK_LISTENER) as MultiPaySdkListener
        super.onCreate(savedInstanceState)
    }

    override fun fragment(): Fragment {
        otpNavigationArgs = intent.getParcelableExtra(EXTRA_OTP_NAVIGATION)
        otpDirectionFrom = intent.getParcelableExtra(EXTRA_OTP_DIRECTION_FROM)
        return OtpFragment.newInstance(otpNavigationArgs!!, otpDirectionFrom!!, listener)
    }
}