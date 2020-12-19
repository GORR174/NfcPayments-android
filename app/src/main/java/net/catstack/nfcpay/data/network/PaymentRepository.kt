package net.catstack.nfcpay.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import net.catstack.nfcpay.common.server.Result
import net.catstack.nfcpay.common.server.getResult
import net.catstack.nfcpay.data.local.AccountRepository
import net.catstack.nfcpay.data.network.api.NfcPaymentApi
import net.catstack.nfcpay.domain.network.request.PaymentRequest

class PaymentRepository(
    private val nfcPaymentApi: NfcPaymentApi,
    private val accountRepository: AccountRepository
) {
    suspend fun createPayment(idempotenceKey: Long, cardNumber: Long, cost: Float, email: String?): Flow<Result<String>> = flow {

        val title = "VISA ***${cardNumber % 10000}"

        val response =
            nfcPaymentApi.createPayment(
                "Bearer " + accountRepository.userToken?.accessToken,
                PaymentRequest(
                    accountRepository.profileModel.company.inn,
                    idempotenceKey,
                    cardNumber,
                    cost,
                    1,
                    accountRepository.deviceInfo,
                    email,
                    title
                )
            )

        emit(response.getResult())
    }.flowOn(Dispatchers.IO)
}