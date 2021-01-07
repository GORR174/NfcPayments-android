package net.catstack.nfcpay.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import net.catstack.nfcpay.common.server.Result
import net.catstack.nfcpay.common.server.getResult
import net.catstack.nfcpay.common.utils.getHistoryDateAndTimeFormat
import net.catstack.nfcpay.common.utils.getHistoryDateFormat
import net.catstack.nfcpay.data.local.AccountRepository
import net.catstack.nfcpay.data.network.api.NfcPaymentApi
import net.catstack.nfcpay.domain.HistoryItemModel

class HistoryRepository(
    private val nfcPaymentApi: NfcPaymentApi,
    private val accountRepository: AccountRepository
) {
    suspend fun getHistory(from: Int, count: Int): Flow<Result<List<HistoryItemModel>>> = flow {

        val response =
            nfcPaymentApi.getHistory(
                "Bearer " + accountRepository.userToken?.accessToken, from, count
            )

        emit(response.getResult())
    }.flowOn(Dispatchers.IO)
        .map {
            if (it is Result.Success) {
                return@map Result.Success(
                    it.data.map { item ->
                        val date = getHistoryDateFormat(item.datetime)
                        val time = getHistoryDateAndTimeFormat(item.datetime)

                        HistoryItemModel(item.title, item.cost, item.datetime, date, time)
                    }
                )
            } else {
                return@map it as Result<List<HistoryItemModel>>
            }
        }
}