package net.catstack.nfcpay.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import net.catstack.nfcpay.common.server.Result
import net.catstack.nfcpay.common.server.getResult
import net.catstack.nfcpay.data.local.AccountRepository
import net.catstack.nfcpay.data.network.api.NfcPaymentApi
import net.catstack.nfcpay.domain.ProfileModel

class ProfileRepository(
    private val nfcPaymentApi: NfcPaymentApi,
    private val accountRepository: AccountRepository
) {
    suspend fun getMyProfile(): Flow<Result<ProfileModel>> = flow {
        val response =
            nfcPaymentApi.getMyProfile("Bearer " + accountRepository.userToken?.accessToken)

        emit(response.getResult())
    }.flowOn(Dispatchers.IO)
        .map {
            if (it is Result.Success) {
                val profileModel = it.data.toProfileModel()

                accountRepository.profileModel = profileModel

                return@map Result.Success(profileModel)
            } else {
                return@map it as Result<ProfileModel>
            }
        }
}