package net.catstack.nfcpay.common.server

import retrofit2.Response

data class ServerResponse<T>(
    val response: T,
    val timestamp: String
)

val <T> Response<ServerResponse<T>>.responseModel: T
    get() {
        return this.body()!!.response
    }