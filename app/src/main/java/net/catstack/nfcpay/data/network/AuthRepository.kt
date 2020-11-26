package net.catstack.nfcpay.data.network

import net.catstack.nfcpay.common.server.Responses
import net.catstack.nfcpay.common.server.call
import net.catstack.nfcpay.data.network.api.NfcPaymentApi
import net.catstack.nfcpay.domain.network.request.DeviceInfo
import net.catstack.nfcpay.domain.network.request.LoginRequest
import net.catstack.nfcpay.domain.network.response.TokenResponseModel

class AuthRepository(private val nfcPaymentApi: NfcPaymentApi) {
    suspend fun login(email: String, password: String): Responses<TokenResponseModel> {
        // TODO: 27.11.2020 Fix DeviceID
        val deviceInfo = DeviceInfo("123", "DEVICE_TYPE_ANDROID")
        val request = LoginRequest(email, password, deviceInfo)
        return call { nfcPaymentApi.login(request) }
    }
}