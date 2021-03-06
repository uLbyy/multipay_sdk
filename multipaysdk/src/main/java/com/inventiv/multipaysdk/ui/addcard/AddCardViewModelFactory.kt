package com.inventiv.multipaysdk.ui.addcard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.inventiv.multipaysdk.repository.WalletRepository

@Suppress("UNCHECKED_CAST")
internal class AddCardViewModelFactory(
    private val walletRepository: WalletRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (AddCardViewModel(walletRepository) as T)
}