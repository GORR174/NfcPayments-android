package net.catstack.nfcpay.ui.payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.catstack.nfcpay.data.local.AccountRepository

class PaymentViewModel(private val accountRepository: AccountRepository) : ViewModel() {
    private val userTokenMutable = MutableLiveData("")
    val userToken: LiveData<String> = userTokenMutable

    fun getProfile() {
        userTokenMutable.postValue(accountRepository.userToken?.accessToken)
    }

    fun logout() {
        accountRepository.logout()
    }
}