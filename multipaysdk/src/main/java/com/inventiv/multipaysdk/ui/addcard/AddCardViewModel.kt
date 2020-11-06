package com.inventiv.multipaysdk.ui.addcard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.inventiv.multipaysdk.data.model.Event
import com.inventiv.multipaysdk.data.model.Resource
import com.inventiv.multipaysdk.data.model.request.AddWallet
import com.inventiv.multipaysdk.data.model.response.AddWalletResponse
import com.inventiv.multipaysdk.repository.CardRepository

internal class AddCardViewModel(
    private val cardRepository: CardRepository
) : ViewModel() {

    private val _addWallet = MutableLiveData<AddWallet>()

    val addWalletResult: LiveData<Event<Resource<AddWalletResponse>>> =
        Transformations
            .switchMap(_addWallet) {
                cardRepository.addWallet(it)
            }

    fun addWallet(cardNumber: String, cvv: String, cardAlias: String) {
        _addWallet.value = AddWallet(
            number = cardNumber,
            cvv = cvv,
            alias = cardAlias
        )
    }
}