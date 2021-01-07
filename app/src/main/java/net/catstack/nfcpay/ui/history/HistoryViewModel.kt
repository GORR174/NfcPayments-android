package net.catstack.nfcpay.ui.history

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
import net.catstack.nfcpay.data.network.HistoryRepository
import net.catstack.nfcpay.domain.HistoryItemModel
import net.catstack.nfcpay.domain.ProfileModel

class HistoryViewModel(private val accountRepository: AccountRepository, private val historyRepository: HistoryRepository) : ViewModel() {
    val profileModel: ProfileModel
        get() = accountRepository.profileModel

    private val _historyResult: MutableLiveData<Result<List<HistoryItemModel>>> = MutableLiveData()
    val historyResult: LiveData<Result<List<HistoryItemModel>>>
        get() = _historyResult

    fun loadHistory() = viewModelScope.launch(Dispatchers.IO) {
        historyRepository.getHistory(0, 100).postToLiveData(_historyResult)
            .collect { _historyResult.postValue(it) }
    }
}