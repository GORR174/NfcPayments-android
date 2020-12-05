package net.catstack.nfcpay.common.server

data class ServerResponseModel<T>(
    val response: T,
    val timestamp: String
)