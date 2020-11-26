package net.catstack.nfcpay

import kotlinx.coroutines.*
import net.catstack.nfcpay.common.server.Responses
import net.catstack.nfcpay.common.server.call
import net.catstack.nfcpay.data.network.api.NfcPaymentApi
import net.catstack.nfcpay.di.appModules
import net.catstack.nfcpay.domain.network.request.DeviceInfo
import net.catstack.nfcpay.domain.network.request.LoginRequest
import org.junit.Test

import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import org.koin.test.get

class ExampleUnitTest : KoinTest {
    @Test
    fun networkLogin() {
        startKoin { modules(appModules) }

        val api: NfcPaymentApi = get()

        runBlocking {
//            val response = call { api.login(LoginRequest("test@gmail.com", "Test", DeviceInfo("123", "DEVICE_TYPE_ANDROID"))) }
            val response = call { api.login(LoginRequest("test1@gmail.com", "password", DeviceInfo("123", "DEVICE_TYPE_ANDROID"))) }

            when (response) {
                is Responses.Successful -> assert(true)
                is Responses.ServerError -> assert(false) { "Server error:\n${response.error}" }
                is Responses.InternetError -> assert(false) { "Internet error" }
            }
        }
    }
}