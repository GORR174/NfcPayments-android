package net.catstack.nfcpay.domain.network.response

data class HistoryItemResponse(
    val title: String,
    val cost: Float,
    val datetime: String
)