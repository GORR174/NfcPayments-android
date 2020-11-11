package net.catstack.nfcpay.ui.register

import androidx.lifecycle.ViewModel
import net.catstack.nfcpay.data.AccountRepository
import net.catstack.nfcpay.domain.TokenModel
import kotlin.random.Random

class RegisterViewModel(private val accountRepository: AccountRepository) : ViewModel() {
    fun register() {
        val token = (100000000 + Random.nextInt(900000000)).toString()
        accountRepository.userToken = TokenModel(token)
    }
}