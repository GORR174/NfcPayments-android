package net.catstack.nfcpay

import net.catstack.nfcpay.common.utils.getHistoryDateAndTimeFormat
import net.catstack.nfcpay.common.utils.getHistoryDateFormat
import org.junit.Test
import org.koin.test.KoinTest

class ExampleUnitTest : KoinTest {
    @Test
    fun testDate() {
        val dateTime = "2021-01-07T18:44:46Z"

        println(getHistoryDateFormat(dateTime))
        println(getHistoryDateAndTimeFormat(dateTime))
    }
}