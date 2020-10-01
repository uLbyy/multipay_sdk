package com.inventiv.multipaysdk.ui.addcard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.inventiv.multipaysdk.MultiPaySdk
import com.inventiv.multipaysdk.MultiPaySdkListener
import com.inventiv.multipaysdk.base.BaseFragment
import com.inventiv.multipaysdk.databinding.FragmentAddCardBinding
import com.inventiv.multipaysdk.repository.CardRepository
import com.inventiv.multipaysdk.util.KEY_MULTIPAY_SDK_LISTENER

internal class AddCardFragment : BaseFragment<FragmentAddCardBinding>() {

    private lateinit var multiPaySdkListener: MultiPaySdkListener

    private val viewModel: AddCardViewModel by viewModels {
        AddCardViewModelFactory(CardRepository(MultiPaySdk.getComponent().apiService()))
    }

    companion object {
        fun newInstance(multiPaySdkListener: MultiPaySdkListener): AddCardFragment =
            AddCardFragment().apply {
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
    ): FragmentAddCardBinding = FragmentAddCardBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        multiPaySdkListener =
            arguments?.getSerializable(KEY_MULTIPAY_SDK_LISTENER) as MultiPaySdkListener
    }
}