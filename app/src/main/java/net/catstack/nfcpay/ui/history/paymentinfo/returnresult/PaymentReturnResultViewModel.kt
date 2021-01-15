package net.catstack.nfcpay.ui.history.paymentinfo.returnresult

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

class PaymentReturnResultViewModel(
    private val paymentRepository: PaymentRepository
) : ViewModel() {
    private val _returnResult: MutableLiveData<Result<String>> = MutableLiveData()
    val returnResult: LiveData<Result<String>>
        get() = _returnResult

    fun returnPayment(paymentId: Long, cardNumber: Long) = viewModelScope.launch(
        Dispatchers.IO) {
        paymentRepository.returnPayment(paymentId, cardNumber).postToLiveData(_returnResult)
            .collect { _returnResult.postValue(it) }
    }
}