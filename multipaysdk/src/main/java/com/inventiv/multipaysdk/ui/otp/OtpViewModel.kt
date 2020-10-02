package com.inventiv.multipaysdk.ui.otp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.inventiv.multipaysdk.data.model.Event
import com.inventiv.multipaysdk.data.model.Resource
import com.inventiv.multipaysdk.data.model.request.ConfirmOtp
import com.inventiv.multipaysdk.data.model.request.ResendOtp
import com.inventiv.multipaysdk.data.model.response.ConfirmOtpResponse
import com.inventiv.multipaysdk.data.model.response.ResendOtpResponse
import com.inventiv.multipaysdk.repository.OtpRepository

internal class OtpViewModel(
    private val otpRepository: OtpRepository
) : ViewModel() {

    private val _confirmOtp = MutableLiveData<Event<ConfirmOtp>>()
    private val _resendOtp = MutableLiveData<Event<ResendOtp>>()

    val confirmOtpResult: LiveData<Event<Resource<ConfirmOtpResponse>>> =
        Transformations
            .switchMap(_confirmOtp) {
                otpRepository.confirmOtp(it.peekContent())
            }

    val resendOtpResult: LiveData<Event<Resource<ResendOtpResponse>>> =
        Transformations
            .switchMap(_resendOtp) {
                otpRepository.resendOtp(it.peekContent())
            }

    fun confirmOtp(verificationCode: String?, otpCode: String) {
        _confirmOtp.value = Event(ConfirmOtp(verificationCode, otpCode))
    }

    fun resendOtp(verificationCode: String?) {
        _resendOtp.value = Event(ResendOtp(verificationCode))
    }
}