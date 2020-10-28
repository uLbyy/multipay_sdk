package com.inventiv.multipaysdk.ui.addcard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.inventiv.multipaysdk.data.model.Event
import com.inventiv.multipaysdk.data.model.Resource
import com.inventiv.multipaysdk.data.model.request.CreateMultinetCard
import com.inventiv.multipaysdk.data.model.response.CreateMultinetCardResponse
import com.inventiv.multipaysdk.data.model.singleton.MultiPayUser
import com.inventiv.multipaysdk.repository.CardRepository

internal class AddCardViewModel(
    private val cardRepository: CardRepository
) : ViewModel() {

    private val _createMultinetCard = MutableLiveData<CreateMultinetCard>()

    val createMultinetCardResult: LiveData<Event<Resource<CreateMultinetCardResponse>>> =
        Transformations
            .switchMap(_createMultinetCard) {
                cardRepository.createMultinet(it)
            }

    fun createMultinetCard(cardNumber: String, cvv: String, cardAlias: String) {
        _createMultinetCard.value = CreateMultinetCard(
            cardNumber = cardNumber,
            cvv = cvv,
            cardAlias = cardAlias,
            phoneNumber = "5331231212",
            clientReferenceNo = MultiPayUser.clientReferenceNo
        )
    }
}