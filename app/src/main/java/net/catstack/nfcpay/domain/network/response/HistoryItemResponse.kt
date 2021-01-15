package net.catstack.nfcpay.domain.network.response

data class HistoryItemResponse(
    val title: String,
    val paymentId: Long,
    val status: HistoryItemStatus,
    val cost: Float,
    val datetime: String
)

enum class HistoryItemStatus {
    SUCCESSFULLY,
    RETURNED
}