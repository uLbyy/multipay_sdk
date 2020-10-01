package com.inventiv.multipaysdk.ui.addcard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.inventiv.multipaysdk.repository.CardRepository

@Suppress("UNCHECKED_CAST")
internal class AddCardViewModelFactory(
    private val cardRepository: CardRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (AddCardViewModel(cardRepository) as T)
}