package net.catstack.nfcpay.domain.network.response

data class RegistrationModel(
    val company: RegistrationCompanyModel
)

data class RegistrationCompanyModel(
    val inn: Long,
    val companyName: String,
    val taxSystem: String,
    val address: String,
    val kkt: String,
    val bill: RegistrationCompanyBillModel
)

data class RegistrationCompanyBillModel(
    val balance: Double
)