package net.catstack.nfcpay.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.catstack.nfcpay.common.server.ResponseStatus
import net.catstack.nfcpay.common.server.toResponseStatus
import net.catstack.nfcpay.data.network.ProfileRepository
import net.catstack.nfcpay.domain.ProfileModel

class HomeViewModel(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val mutableResponseStatus: MutableLiveData<ResponseStatus<ProfileModel>> =
        MutableLiveData(ResponseStatus.Default())
    val responseStatus: LiveData<ResponseStatus<ProfileModel>>
        get() = mutableResponseStatus

    fun loadProfile() = viewModelScope.launch(Dispatchers.IO) {
        mutableResponseStatus.postValue(ResponseStatus.Loading())
        val responseStatus = profileRepository.getMyProfile().toResponseStatus()
        mutableResponseStatus.postValue(responseStatus)
    }
}