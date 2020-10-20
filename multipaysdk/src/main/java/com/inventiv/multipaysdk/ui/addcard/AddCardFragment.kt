package com.inventiv.multipaysdk.ui.addcard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.inventiv.multipaysdk.MultiPaySdk
import com.inventiv.multipaysdk.base.BaseFragment
import com.inventiv.multipaysdk.data.model.EventObserver
import com.inventiv.multipaysdk.data.model.Resource
import com.inventiv.multipaysdk.databinding.FragmentAddCardBinding
import com.inventiv.multipaysdk.repository.CardRepository
import com.inventiv.multipaysdk.util.hideKeyboard
import com.inventiv.multipaysdk.util.showSnackBarAlert

internal class AddCardFragment : BaseFragment<FragmentAddCardBinding>() {

    private val viewModel: AddCardViewModel by viewModels {
        AddCardViewModelFactory(CardRepository(MultiPaySdk.getComponent().apiService()))
    }

    companion object {
        fun newInstance(): AddCardFragment =
            AddCardFragment().apply {
            }
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentAddCardBinding = FragmentAddCardBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeCreateMultinetCard()
        viewModel.createMultinetCard("6656900006030610", "524", "denemetest")
    }

    private fun subscribeCreateMultinetCard() {
        viewModel.createMultinetCardResult.observe(viewLifecycleOwner, EventObserver { resource ->
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

    private fun createMultinetCard() {
        requireBinding().textInputCardNumber.hideKeyboard()
        requireBinding().textInputCvv.hideKeyboard()
        requireBinding().textInputCardAlias.hideKeyboard()
        val cardNumber = requireBinding().textInputEditCardNumber.text.toString().trim()
        val cvv = requireBinding().textInputEditCvv.text.toString().trim()
        val cardAlias = requireBinding().textInputEditCardAlias.text.toString().trim()
        viewModel.createMultinetCard(cardNumber, cvv, cardAlias)
    }
}