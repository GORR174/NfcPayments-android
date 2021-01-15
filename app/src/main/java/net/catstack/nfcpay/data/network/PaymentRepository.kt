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
import net.catstack.nfcpay.domain.network.request.PaymentReturnRequest

class PaymentRepository(
    private val nfcPaymentApi: NfcPaymentApi,
    private val accountRepository: AccountRepository
) {
    suspend fun createPayment(idempotenceKey: Long, cardName: String, cardNumber: Long, cost: Float, email: String?): Flow<Result<String>> = flow {

        val title = "$cardName ***${cardNumber % 10000}"

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

    suspend fun returnPayment(paymentId: Long, cardNumber: Long): Flow<Result<String>> = flow {
        val response =
            nfcPaymentApi.returnPayment(
                "Bearer " + accountRepository.userToken?.accessToken,
                PaymentReturnRequest(
                    paymentId,
                    accountRepository.profileModel.company.inn,
                    cardNumber,
                    accountRepository.deviceInfo
                )
            )

        emit(response.getResult())
    }.flowOn(Dispatchers.IO)
}