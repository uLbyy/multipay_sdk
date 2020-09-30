package com.inventiv.multipaysdk.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.inventiv.multipaysdk.MultiPaySdk
import com.inventiv.multipaysdk.MultiPaySdkListener
import com.inventiv.multipaysdk.R
import com.inventiv.multipaysdk.base.BaseFragment
import com.inventiv.multipaysdk.data.api.callback.NetworkCallback
import com.inventiv.multipaysdk.data.api.error.ApiError
import com.inventiv.multipaysdk.data.model.request.LoginGsm
import com.inventiv.multipaysdk.data.model.request.LoginInfoGsm
import com.inventiv.multipaysdk.data.model.response.Result
import com.inventiv.multipaysdk.databinding.FragmentLoginBinding
import com.inventiv.multipaysdk.util.Formatter
import com.inventiv.multipaysdk.util.KEY_MULTIPAY_SDK_LISTENER
import com.inventiv.multipaysdk.util.hideKeyboard
import com.inventiv.multipaysdk.view.listener.MaskWatcher


internal class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private lateinit var maskWatcher: MaskWatcher
    private lateinit var multiPaySdkListener: MultiPaySdkListener


    companion object {
        fun newInstance(multiPaySdkListener: MultiPaySdkListener): LoginFragment =
            LoginFragment().apply {
                val args = Bundle().apply {
                    putSerializable(KEY_MULTIPAY_SDK_LISTENER, multiPaySdkListener)
                }
                arguments = args
            }
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        multiPaySdkListener =
            arguments?.getSerializable(KEY_MULTIPAY_SDK_LISTENER) as MultiPaySdkListener
        val maskPhone = getString(R.string.mask_phone)
        maskWatcher = MaskWatcher(requireBinding().textInputEditEmailOrGsm, maskPhone)
        requireBinding().textInputEditEmailOrGsm.addTextChangedListener(maskWatcher)
        requireBinding().buttonLogin.setOnClickListener {
            loginClicked()
        }
    }

    override fun onDestroyView() {
        requireBinding().textInputEditEmailOrGsm.removeTextChangedListener(maskWatcher)
        super.onDestroyView()
    }

    private fun loginClicked() {
        requireBinding().textInputEditEmailOrGsm.hideKeyboard()
        requireBinding().textInputEditPassword.hideKeyboard()

        val emailOrGsm = requireBinding().textInputEditEmailOrGsm.text.toString().trim()
        val password = requireBinding().textInputEditPassword.text.toString().trim()
        val loginRequestBody =
            LoginGsm(LoginInfoGsm(Formatter.servicePhoneNumber(emailOrGsm), password))

        MultiPaySdk.getComponent().apiService()
            .loginRequest(loginRequestBody, object : NetworkCallback<Result> {
                override fun onSuccess(response: Result?) {
                    multiPaySdkListener.onSuccess(response)
                }

                override fun onError(error: ApiError) {
                    multiPaySdkListener.onFailure(error.message, error.statusCode)
                }
            })

    }
}