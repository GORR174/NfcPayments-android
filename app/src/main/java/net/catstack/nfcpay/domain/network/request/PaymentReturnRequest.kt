package net.catstack.nfcpay.domain.network.request

data class PaymentReturnRequest(
    val paymentId: Long,
    val inn: Long,
    val payerCN: Long,
    val deviceInfo: DeviceInfo,
)