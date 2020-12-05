package net.catstack.nfcpay.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import net.catstack.nfcpay.common.server.Result
import net.catstack.nfcpay.common.server.postToLiveData
import net.catstack.nfcpay.data.local.AccountRepository
import net.catstack.nfcpay.data.network.AuthRepository
import net.catstack.nfcpay.domain.TokenModel
import net.catstack.nfcpay.domain.network.response.TokenResponseModel

class LoginViewModel(
    private val accountRepository: AccountRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    val savedEmail: String?
        get() = accountRepository.email

    val savedPassword: String?
        get() = accountRepository.password

    private val _loginResult: MutableLiveData<Result<TokenResponseModel>> = MutableLiveData()
    val loginResult: LiveData<Result<TokenResponseModel>>
        get() = _loginResult

    fun login(email: String, password: String) = viewModelScope.launch {
        authRepository.login(email, password).postToLiveData(_loginResult)
            .collect {
                if (it is Result.Success) {
                    accountRepository.email = email
                    accountRepository.password = password
                    accountRepository.userToken = TokenModel(it.data.token)
                }
                _loginResult.postValue(it)
            }
    }
}