package net.catstack.nfcpay.domain.network.response

data class TokenResponseModel(
    val token: String,
    val tokenType: String
)