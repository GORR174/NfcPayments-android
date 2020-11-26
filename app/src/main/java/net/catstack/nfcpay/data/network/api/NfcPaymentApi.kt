package net.catstack.nfcpay.data.network.api

import net.catstack.nfcpay.common.server.ServerResponse
import net.catstack.nfcpay.domain.network.response.TokenResponseModel
import net.catstack.nfcpay.domain.network.request.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface NfcPaymentApi {
    @POST("/nfc-api/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<ServerResponse<TokenResponseModel>>
}