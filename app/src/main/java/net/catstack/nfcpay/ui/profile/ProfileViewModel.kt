package net.catstack.nfcpay.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.catstack.nfcpay.data.AccountRepository

class ProfileViewModel(private val accountRepository: AccountRepository) : ViewModel() {
    private val userTokenMutable = MutableLiveData("")
    val userToken: LiveData<String> = userTokenMutable

    fun getProfile() {
        userTokenMutable.postValue(accountRepository.userToken?.accessToken)
    }

    fun logout() {
        accountRepository.logout()
    }
}