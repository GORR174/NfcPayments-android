package net.catstack.nfcpay.ui.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.catstack.nfcpay.common.server.ResponseStatus
import net.catstack.nfcpay.common.server.toResponseStatus
import net.catstack.nfcpay.data.local.AccountRepository
import net.catstack.nfcpay.data.network.AuthRepository
import net.catstack.nfcpay.domain.TokenModel
import net.catstack.nfcpay.domain.network.response.TokenResponseModel
import okhttp3.internal.toLongOrDefault
import kotlin.random.Random

class RegisterViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val mutableResponseStatus: MutableLiveData<ResponseStatus<String>> = MutableLiveData(ResponseStatus.Default())
    val responseStatus: LiveData<ResponseStatus<String>>
        get() = mutableResponseStatus

    private val mutableIsLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = mutableIsLoading

    fun register(name: String, phone: String, email: String, inn: String)  = viewModelScope.launch(Dispatchers.IO) {
        mutableIsLoading.postValue(true)
        val responseStatus = authRepository.register(name, phone, email, inn.toLongOrDefault(0)).toResponseStatus()
        mutableResponseStatus.postValue(responseStatus)
        mutableIsLoading.postValue(false)
    }
}