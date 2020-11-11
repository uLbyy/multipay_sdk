package com.inventiv.multipaysdk.ui.wallet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.inventiv.multipaysdk.data.model.Event
import com.inventiv.multipaysdk.data.model.Resource
import com.inventiv.multipaysdk.data.model.response.WalletResponse
import com.inventiv.multipaysdk.repository.WalletRepository

internal class WalletViewModel(
    private val walletRepository: WalletRepository
) : ViewModel() {

    private val _walletsListItem = MutableLiveData<Unit>()

    var selectedWallet = MutableLiveData<WalletResponse>()

    val walletsListItemResult: LiveData<Event<Resource<List<WalletListItem>>>> =
        Transformations
            .switchMap(_walletsListItem) {
                walletRepository.walletsListItem()
            }

    fun walletsListItem() {
        _walletsListItem.value = Unit
    }

    fun selectWallet(walletResponse: WalletResponse) {
        selectedWallet.value = walletResponse
    }

}