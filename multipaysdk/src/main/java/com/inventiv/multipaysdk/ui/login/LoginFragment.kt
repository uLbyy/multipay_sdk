package com.inventiv.multipaysdk.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.inventiv.multipaysdk.MultiPaySdk
import com.inventiv.multipaysdk.MultiPaySdkListener
import com.inventiv.multipaysdk.R
import com.inventiv.multipaysdk.base.BaseFragment
import com.inventiv.multipaysdk.data.model.EventObserver
import com.inventiv.multipaysdk.data.model.Resource
import com.inventiv.multipaysdk.databinding.FragmentLoginBinding
import com.inventiv.multipaysdk.repository.AuthenticationRepository
import com.inventiv.multipaysdk.ui.addcard.AddCardActivity
import com.inventiv.multipaysdk.util.KEY_MULTIPAY_SDK_LISTENER
import com.inventiv.multipaysdk.util.hideKeyboard
import com.inventiv.multipaysdk.util.showSnackBarAlert
import com.inventiv.multipaysdk.view.listener.MaskWatcher


internal class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private lateinit var maskWatcher: MaskWatcher
    private lateinit var multiPaySdkListener: MultiPaySdkListener

    private val viewModel: LoginViewModel by viewModels {
        LoginViewModelFactory(AuthenticationRepository(MultiPaySdk.getComponent().apiService()))
    }

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
        subscribeLogin()
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

    private fun subscribeLogin() {
        viewModel.loginResult.observe(viewLifecycleOwner, EventObserver { resource ->
            when (resource) {
                is Resource.Loading -> {
                    setLayoutProgressVisibility(View.VISIBLE)
                }
                is Resource.Success -> {
                    startActivity(AddCardActivity.newIntent(requireActivity(), multiPaySdkListener))
                    setLayoutProgressVisibility(View.GONE)
                }
                is Resource.Failure -> {
                    showSnackBarAlert(resource.message)
                    setLayoutProgressVisibility(View.GONE)
                }
            }
        })
    }

    private fun setLayoutProgressVisibility(visibility: Int) {
        requireBinding().loginProgress.layoutProgress.visibility = visibility
    }

    private fun loginClicked() {
        requireBinding().textInputEditEmailOrGsm.hideKeyboard()
        requireBinding().textInputEditPassword.hideKeyboard()
        val emailOrGsm = requireBinding().textInputEditEmailOrGsm.text.toString().trim()
        val password = requireBinding().textInputEditPassword.text.toString().trim()
        viewModel.login(emailOrGsm, password)
    }
}