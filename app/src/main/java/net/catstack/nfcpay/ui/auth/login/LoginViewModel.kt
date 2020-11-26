package net.catstack.nfcpay.ui.auth.login

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
import net.catstack.nfcpay.domain.network.response.TokenResponseModel

// TODO: 27.11.2020 Refactor responseStatus
class LoginViewModel(
    private val accountRepository: AccountRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    val savedEmail: String?
        get() = accountRepository.email

    val savedPassword: String?
        get() = accountRepository.password

    private val mutableResponseStatus: MutableLiveData<ResponseStatus<TokenResponseModel>> = MutableLiveData(ResponseStatus.Default())
    val responseStatus: LiveData<ResponseStatus<TokenResponseModel>>
        get() = mutableResponseStatus

    private val mutableIsLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = mutableIsLoading

    fun login(email: String, password: String) = viewModelScope.launch(Dispatchers.IO) {
        mutableIsLoading.postValue(true)
        val responseStatus = authRepository.login(email, password).toResponseStatus()
        if (responseStatus is ResponseStatus.Successful) {
            accountRepository.email = email
            accountRepository.password = password
        }
        mutableResponseStatus.postValue(responseStatus)
        mutableIsLoading.postValue(false)
    }
}