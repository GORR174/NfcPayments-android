package net.catstack.nfcpay.domain.network.request

data class LoginRequest(
    val email: String,
    val password: String,
    val deviceInfo: DeviceInfo
)

data class DeviceInfo(
    val deviceId: String,
    val deviceType: String
)