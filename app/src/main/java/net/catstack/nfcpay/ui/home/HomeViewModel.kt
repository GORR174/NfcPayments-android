package net.catstack.nfcpay.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import net.catstack.nfcpay.common.server.Result
import net.catstack.nfcpay.common.server.postToLiveData
import net.catstack.nfcpay.data.local.AccountRepository
import net.catstack.nfcpay.data.network.ProfileRepository
import net.catstack.nfcpay.domain.ProfileModel

class HomeViewModel(
    private val profileRepository: ProfileRepository,
    private val accountRepository: AccountRepository
) : ViewModel() {

    private val _profileResult: MutableLiveData<Result<ProfileModel>> = MutableLiveData()
    val profileResult: LiveData<Result<ProfileModel>>
        get() = _profileResult

    val archivedUser: ProfileModel
        get() = accountRepository.profileModel

    fun loadProfile() = viewModelScope.launch(Dispatchers.IO) {
        profileRepository.getMyProfile().postToLiveData(_profileResult)
            .collect { _profileResult.postValue(it) }
    }

    fun logout() {
        accountRepository.logout()
    }
}