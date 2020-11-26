package net.catstack.nfcpay.ui.login

import androidx.lifecycle.ViewModel
import net.catstack.nfcpay.data.local.AccountRepository
import net.catstack.nfcpay.domain.TokenModel
import kotlin.random.Random

class LoginViewModel(private val accountRepository: AccountRepository) : ViewModel() {
    fun login() {
        val token = (100000000 + Random.nextInt(900000000)).toString()
        accountRepository.userToken = TokenModel(token)
    }
}