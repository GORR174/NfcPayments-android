package net.catstack.nfcpay.data.network.api

import net.catstack.nfcpay.common.server.ServerResponse
import net.catstack.nfcpay.domain.network.request.ApplicationRequest
import net.catstack.nfcpay.domain.network.response.TokenResponseModel
import net.catstack.nfcpay.domain.network.request.LoginRequest
import net.catstack.nfcpay.domain.network.response.ProfileModelResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface NfcPaymentApi {
    @POST("/nfc-api/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<ServerResponse<TokenResponseModel>>

    @POST("/nfc-api/applications/send")
    suspend fun sendApplication(@Body applicationRequest: ApplicationRequest): Response<ServerResponse<String>>

    @GET("/nfc-api/users/me")
    suspend fun getMyProfile(@Header("Authorization") auth: String): Response<ServerResponse<ProfileModelResponse>>
}