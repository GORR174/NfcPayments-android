package net.catstack.nfcpay.data.network

import net.catstack.nfcpay.common.server.Responses
import net.catstack.nfcpay.common.server.call
import net.catstack.nfcpay.data.local.AccountRepository
import net.catstack.nfcpay.data.network.api.NfcPaymentApi
import net.catstack.nfcpay.domain.ProfileModel

class ProfileRepository(
    private val nfcPaymentApi: NfcPaymentApi,
    private val accountRepository: AccountRepository
) {
    suspend fun getMyProfile(): Responses<ProfileModel> {
        val response =
            call { nfcPaymentApi.getMyProfile("Bearer " + accountRepository.userToken?.accessToken) }

        return when (response) {
            is Responses.Successful -> {
                val profileModel = response.response.toProfileModel()
                accountRepository.profileModel = profileModel
                return Responses.Successful(profileModel)
            }
            is Responses.ServerError -> Responses.ServerError(response.error)
            is Responses.InternetError -> Responses.InternetError()
        }
    }
}