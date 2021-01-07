package net.catstack.nfcpay.ui.history

import androidx.lifecycle.ViewModel
import net.catstack.nfcpay.data.local.AccountRepository
import net.catstack.nfcpay.domain.ProfileModel

class HistoryViewModel(private val accountRepository: AccountRepository) : ViewModel() {
    val profileModel: ProfileModel
        get() = accountRepository.profileModel
}