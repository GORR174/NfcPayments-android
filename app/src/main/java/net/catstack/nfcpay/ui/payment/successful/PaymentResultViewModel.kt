package net.catstack.nfcpay.ui.payment.successful

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import net.catstack.nfcpay.common.server.Result
import net.catstack.nfcpay.common.server.postToLiveData
import net.catstack.nfcpay.data.network.PaymentRepository

class PaymentResultViewModel(
    private val paymentRepository: PaymentRepository
) : ViewModel() {
    private val _paymentResult: MutableLiveData<Result<String>> = MutableLiveData()
    val paymentResult: LiveData<Result<String>>
        get() = _paymentResult

    fun pay(idempotenceKey: Long, cardNumber: Long, cost: Float, email: String?) = viewModelScope.launch(Dispatchers.IO) {
        paymentRepository.createPayment(idempotenceKey, cardNumber, cost, email).postToLiveData(_paymentResult)
            .collect { _paymentResult.postValue(it) }
    }
}