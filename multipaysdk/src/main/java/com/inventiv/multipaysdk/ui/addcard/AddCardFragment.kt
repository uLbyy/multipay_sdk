package com.inventiv.multipaysdk.ui.addcard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.inventiv.multipaysdk.MultiPaySdk
import com.inventiv.multipaysdk.R
import com.inventiv.multipaysdk.base.BaseFragment
import com.inventiv.multipaysdk.data.model.EventObserver
import com.inventiv.multipaysdk.data.model.Resource
import com.inventiv.multipaysdk.databinding.FragmentAddCardBinding
import com.inventiv.multipaysdk.repository.CardRepository
import com.inventiv.multipaysdk.util.*
import com.inventiv.multipaysdk.view.listener.MaskCardNumberWatcher
import com.inventiv.multipaysdk.view.listener.MaskCardNumberWatcherView

internal class AddCardFragment : BaseFragment<FragmentAddCardBinding>(), MaskCardNumberWatcherView {

    private val viewModel: AddCardViewModel by viewModels {
        AddCardViewModelFactory(CardRepository(MultiPaySdk.getComponent().apiService()))
    }

    companion object {
        fun newInstance(): AddCardFragment = AddCardFragment()
    }

    override fun onResume() {
        super.onResume()
        showToolbar()
        toolbarBack()
        title(R.string.add_card_navigation_title)
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentAddCardBinding = FragmentAddCardBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeAddWallet()
        requireBinding().textInputEditCardNumber.addTextChangedListener(MaskCardNumberWatcher(this@AddCardFragment))
        requireBinding().buttonContinue.setOnClickListener {
            addWallet()
        }
    }

    private fun subscribeAddWallet() {
        viewModel.addWalletResult.observe(viewLifecycleOwner, EventObserver { resource ->
            when (resource) {
                is Resource.Loading -> {
                    setLayoutProgressVisibility(View.VISIBLE)
                }
                is Resource.Success -> {
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
        requireBinding().addCardProgress.layoutProgress.visibility = visibility
    }

    private fun addWallet() {
        requireBinding().textInputEditCardAlias.hideKeyboard()
        requireBinding().textInputEditCardNumber.hideKeyboard()
        requireBinding().textInputEditCardCvv.hideKeyboard()
        val cardAlias = requireBinding().textInputEditCardAlias.text.toString().trim()
        val cardNumber = requireBinding().textInputEditCardNumber.text.toString().trim()
        val cvv = requireBinding().textInputEditCardCvv.text.toString().trim()
        viewModel.addWallet(Formatter.stringToNumeric(cardNumber), cvv, cardAlias)
    }
}