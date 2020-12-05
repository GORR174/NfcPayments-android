package net.catstack.nfcpay.ui.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import net.catstack.nfcpay.common.server.Result
import net.catstack.nfcpay.common.server.postToLiveData
import net.catstack.nfcpay.data.network.AuthRepository
import okhttp3.internal.toLongOrDefault

class RegisterViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _registerResult: MutableLiveData<Result<String>> = MutableLiveData()
    val registerResult: LiveData<Result<String>>
        get() = _registerResult

    fun register(name: String, phone: String, email: String, inn: String)  = viewModelScope.launch(Dispatchers.IO) {
        val replacedPhone = phone.replace("-", "").replace(" ", "")
        authRepository.register(name, replacedPhone, email, inn.toLongOrDefault(0))
            .postToLiveData(_registerResult)
            .collect { _registerResult.postValue(it) }
    }
}