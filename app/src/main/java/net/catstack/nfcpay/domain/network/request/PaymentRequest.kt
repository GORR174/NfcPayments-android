package net.catstack.nfcpay.domain.network.request

data class PaymentRequest(
    val inn: Long,
    val idempotenceKey: Long,
    val payerCN: Long,
    val cost: Float,
    val amount: Int,
    val deviceInfo: DeviceInfo,
    val buyerEmail: String?,
    val title: String
)