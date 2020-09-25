package com.inventiv.multipaysdk.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.Response
import com.inventiv.multipaysdk.MultiPay
import com.inventiv.multipaysdk.R
import com.inventiv.multipaysdk.base.BaseFragment
import com.inventiv.multipaysdk.data.api.RequestManager
import com.inventiv.multipaysdk.data.model.request.LoginGsm
import com.inventiv.multipaysdk.data.model.request.LoginInfoGsm
import com.inventiv.multipaysdk.data.model.response.LoginResponse
import com.inventiv.multipaysdk.databinding.FragmentLoginBinding
import com.inventiv.multipaysdk.util.Formatter
import com.inventiv.multipaysdk.util.hideKeyboard
import com.inventiv.multipaysdk.view.listener.MaskWatcher


class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private lateinit var maskWatcher: MaskWatcher

    companion object {
        fun newInstance(): LoginFragment = LoginFragment().apply {
            val args = Bundle().apply {
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
        val url = "https://test-multinet-multipay-api.inventiv.services/MultiUService/SdkLogin"
        val headers = mutableMapOf<String, String>()
        headers["device-app-version"] = "4.4.5"
        val loginRequestBody =
            LoginGsm(LoginInfoGsm(Formatter.servicePhoneNumber(emailOrGsm), password))
        val loginRequest = RequestManager.GsonRequest<LoginGsm, LoginResponse>(
            url,
            loginRequestBody,
            LoginResponse::class.java,
            headers,
            Response.Listener<LoginResponse> { response ->
                Toast.makeText(requireActivity(), response.toString(), Toast.LENGTH_LONG).show()
            },
            Response.ErrorListener {
                Toast.makeText(requireActivity(), "FAIL : ${it.message}", Toast.LENGTH_LONG).show()
            }
        )

        MultiPay.addToRequestQueue(loginRequest)
    }
}