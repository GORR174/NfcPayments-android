package net.catstack.nfcpay.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import net.catstack.nfcpay.common.server.Result
import net.catstack.nfcpay.common.server.postToLiveData
import net.catstack.nfcpay.data.local.AccountRepository
import net.catstack.nfcpay.data.network.AuthRepository
import net.catstack.nfcpay.data.network.ProfileRepository
import net.catstack.nfcpay.domain.TokenModel
import net.catstack.nfcpay.domain.network.response.TokenResponseModel

class LoginViewModel(
    private val accountRepository: AccountRepository,
    private val authRepository: AuthRepository,
    private val profileRepository: ProfileRepository
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
            .collect { tokenResult ->
                if (tokenResult is Result.Success) {
                    accountRepository.email = email
                    accountRepository.password = password
                    accountRepository.userToken = TokenModel(tokenResult.data.token)

                    profileRepository.getMyProfile().onStart { _loginResult.postValue(Result.Loading) }
                        .catch {
                            _loginResult.postValue(Result.InternetError)
                            it.printStackTrace()
                        }
                        .collect {
                            if (it is Result.Success) {
                                accountRepository.profileModel = it.data
                                _loginResult.postValue(tokenResult)
                            } else if (it is Result.ServerError) {
                                _loginResult.postValue(it)
                            }
                        }
                } else {
                    _loginResult.postValue(tokenResult)
                }
            }
    }
}