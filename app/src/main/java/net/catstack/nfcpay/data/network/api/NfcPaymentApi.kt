package net.catstack.nfcpay.data.network.api

import net.catstack.nfcpay.common.aliases.ServerResponse
import net.catstack.nfcpay.domain.network.request.ApplicationRequest
import net.catstack.nfcpay.domain.network.response.TokenResponseModel
import net.catstack.nfcpay.domain.network.request.LoginRequest
import net.catstack.nfcpay.domain.network.request.PaymentRequest
import net.catstack.nfcpay.domain.network.response.HistoryItemResponse
import net.catstack.nfcpay.domain.network.response.ProfileModelResponse
import retrofit2.http.*

interface NfcPaymentApi {
    @POST("/nfc-api/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): ServerResponse<TokenResponseModel>

    @POST("/nfc-api/applications/send")
    suspend fun sendApplication(@Body applicationRequest: ApplicationRequest): ServerResponse<String>

    @GET("/nfc-api/users/me")
    suspend fun getMyProfile(@Header("Authorization") auth: String): ServerResponse<ProfileModelResponse>

    @POST("/nfc-api/payment/create")
    suspend fun createPayment(
        @Header("Authorization") auth: String,
        @Body paymentRequest: PaymentRequest
    ): ServerResponse<String>

    @GET("/nfc-api/history/get")
    suspend fun getHistory(
        @Header("Authorization") auth: String,
        @Query("from") from: Int,
        @Query("count") count: Int
    ): ServerResponse<List<HistoryItemResponse>>
}