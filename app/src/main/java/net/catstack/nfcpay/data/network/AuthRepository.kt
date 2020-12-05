package net.catstack.nfcpay.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import net.catstack.nfcpay.common.server.*
import net.catstack.nfcpay.data.network.api.NfcPaymentApi
import net.catstack.nfcpay.domain.network.request.ApplicationRequest
import net.catstack.nfcpay.domain.network.request.DeviceInfo
import net.catstack.nfcpay.domain.network.request.LoginRequest
import net.catstack.nfcpay.domain.network.response.TokenResponseModel

class AuthRepository(private val nfcPaymentApi: NfcPaymentApi) {
    suspend fun login(email: String, password: String): Flow<Result<TokenResponseModel>> = flow {
        // TODO: 27.11.2020 Fix DeviceID
        val deviceInfo = DeviceInfo("123", "DEVICE_TYPE_ANDROID")
        val request = LoginRequest(email, password, deviceInfo)

        emit(nfcPaymentApi.login(request).getResult())
    }.flowOn(Dispatchers.IO)

    suspend fun register(name: String, phone: String, email: String, inn: Long): Flow<Result<String>> = flow {
        val request = ApplicationRequest(name, phone, email, inn)
        emit(nfcPaymentApi.sendApplication(request).getResult())
    }.flowOn(Dispatchers.IO)
}