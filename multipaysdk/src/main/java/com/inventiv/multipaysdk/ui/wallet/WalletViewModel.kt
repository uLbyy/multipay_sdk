package com.inventiv.multipaysdk.ui.wallet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.inventiv.multipaysdk.data.model.Event
import com.inventiv.multipaysdk.data.model.Resource
import com.inventiv.multipaysdk.data.model.response.WalletsResponse
import com.inventiv.multipaysdk.repository.WalletRepository

internal class WalletViewModel(
    private val walletRepository: WalletRepository
) : ViewModel() {

    private val _wallets = MutableLiveData<Unit>()

    val walletsResult: LiveData<Event<Resource<WalletsResponse>>> =
        Transformations
            .switchMap(_wallets) {
                walletRepository.wallets()
            }

    fun wallets() {
        _wallets.value = Unit
    }

}