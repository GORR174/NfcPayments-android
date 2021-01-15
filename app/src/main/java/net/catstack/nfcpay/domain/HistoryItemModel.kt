package net.catstack.nfcpay.domain

import net.catstack.nfcpay.domain.network.response.HistoryItemStatus
import java.io.Serializable

data class HistoryItemModel(
    val title: String,
    val paymentId: Long,
    val status: HistoryItemStatus,
    val cost: Float,
    val datetime: String,
    val historyDate: String,
    val paymentDateAndTime: String
) : Serializable