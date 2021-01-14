package net.catstack.nfcpay.domain

import java.io.Serializable

data class HistoryItemModel(
    val title: String,
    val cost: Float,
    val datetime: String,
    val status: String,
    val historyDate: String,
    val paymentDateAndTime: String
) : Serializable