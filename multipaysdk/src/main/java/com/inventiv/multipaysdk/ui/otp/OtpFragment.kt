package com.inventiv.multipaysdk.ui.otp

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.inventiv.multipaysdk.MultiPaySdk
import com.inventiv.multipaysdk.R
import com.inventiv.multipaysdk.base.BaseFragment
import com.inventiv.multipaysdk.data.model.EventObserver
import com.inventiv.multipaysdk.data.model.Resource
import com.inventiv.multipaysdk.data.model.type.OtpDirectionFrom
import com.inventiv.multipaysdk.databinding.FragmentOtpBinding
import com.inventiv.multipaysdk.repository.OtpRepository
import com.inventiv.multipaysdk.util.*
import com.inventiv.multipaysdk.view.listener.SimpleTextWatcher
import java.util.concurrent.TimeUnit

internal class OtpFragment : BaseFragment<FragmentOtpBinding>() {

    private var otpNavigationArgs: OtpNavigationArgs? = null
    private var otpDirectionFrom: OtpDirectionFrom? = null
    private lateinit var countDownTimer: CountDownTimer

    private val viewModel: OtpViewModel by viewModels {
        OtpViewModelFactory(OtpRepository(MultiPaySdk.getComponent().apiService()))
    }

    companion object {
        fun newInstance(
            otpNavigationArgs: OtpNavigationArgs,
            otpDirectionFrom: OtpDirectionFrom
        ): OtpFragment =
            OtpFragment().apply {
                val args = Bundle().apply {
                    putParcelable(ARG_OTP_NAVIGATION, otpNavigationArgs)
                    putParcelable(ARG_OTP_DIRECTION_FROM, otpDirectionFrom)
                }
                arguments = args
            }
    }

    private var simpleTextWatcher: TextWatcher = object : SimpleTextWatcher {
        override fun afterTextChanged(s: Editable?) {
            val otpCode = s.toString()
            val otpLength = otpCode.length
            if (otpLength == resources.getInteger(R.integer.otp_length)) {
                viewModel.confirmOtp(otpNavigationArgs?.verificationCode, otpCode)
            }
        }
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentOtpBinding = FragmentOtpBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeConfirmOtp()
        subscribeResendOtp()
        otpNavigationArgs = arguments?.getParcelable(ARG_OTP_NAVIGATION)
        otpDirectionFrom = arguments?.getParcelable(ARG_OTP_DIRECTION_FROM)
        requireBinding().viewPin.addTextChangedListener(simpleTextWatcher)
        requireBinding().textTitle.text = getString(
            R.string.otp_description,
            Formatter.formatPhoneNumber(otpNavigationArgs?.gsmNumber, true)
        )
        requireBinding().viewPin.showKeyboard()
        setupAndStartCountDownTimer()
        requireBinding().buttonResend.setOnClickListener {
            viewModel.resendOtp(otpNavigationArgs?.verificationCode)
        }
    }

    private fun setupAndStartCountDownTimer() {
        requireBinding().buttonResend.alpha = 0.3f
        requireBinding().buttonResend.isClickable = false
        val seconds = otpNavigationArgs?.remainingTime?.toLong() ?: 100L
        countDownTimer = object : CountDownTimer(TimeUnit.SECONDS.toMillis(seconds), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val formattedTimerText =
                    String.format(
                        getString(R.string.otp_remaining_time),
                        (millisUntilFinished / 1000)
                    )
                requireBinding().textRemainingTime.text = formattedTimerText
            }

            override fun onFinish() {
                requireBinding().buttonResend.alpha = 1f
                requireBinding().buttonResend.isClickable = true
            }
        }
        countDownTimer.start()
    }

    private fun subscribeConfirmOtp() {
        viewModel.confirmOtpResult.observe(viewLifecycleOwner, EventObserver { resource ->
            when (resource) {
                is Resource.Loading -> {
                    setLayoutProgressVisibility(View.VISIBLE)
                }
                is Resource.Success -> {
                    setLayoutProgressVisibility(View.GONE)
                    val cardToken = resource.data?.cardToken
                    when (otpDirectionFrom) {
                        OtpDirectionFrom.LOGIN -> {
                            // TODO : manage navigation
                        }
                        OtpDirectionFrom.CREATE_CARD -> {
                            // TODO : manage navigation
                        }
                    }
                }
                is Resource.Failure -> {
                    showSnackBarAlert(resource.message)
                    setLayoutProgressVisibility(View.GONE)
                }
            }
        })
    }

    private fun subscribeResendOtp() {
        viewModel.resendOtpResult.observe(viewLifecycleOwner, EventObserver { resource ->
            when (resource) {
                is Resource.Loading -> {
                    setLayoutProgressVisibility(View.VISIBLE)
                }
                is Resource.Success -> {
                    setLayoutProgressVisibility(View.GONE)
                    otpNavigationArgs =
                        OtpNavigationArgs(resource.data?.verificationCode, "5309600090", 100)
                    setupAndStartCountDownTimer()
                }
                is Resource.Failure -> {
                    showSnackBarAlert(resource.message)
                    setLayoutProgressVisibility(View.GONE)
                }
            }
        })
    }

    private fun setLayoutProgressVisibility(visibility: Int) {
        requireBinding().otpProgress.layoutProgress.visibility = visibility
    }

    override fun onDestroyView() {
        requireBinding().viewPin.removeTextChangedListener(simpleTextWatcher)
        countDownTimer.cancel()
        super.onDestroyView()
    }
}