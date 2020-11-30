package net.catstack.nfcpay.domain.network.request

data class ApplicationRequest(
    val name: String,
    val phone: String,
    val email: String,
    val inn: Long,
)