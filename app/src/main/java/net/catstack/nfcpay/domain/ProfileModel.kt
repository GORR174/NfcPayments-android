package net.catstack.nfcpay.domain

data class ProfileModel(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val patronymic: String,
    val email: String,
    val phone: String,
    val company: CompanyModel
)

data class CompanyModel(
    val inn: Long,
    val companyName: String,
    val taxSystem: String,
    val address: String,
    val kkt: Long,
    val billBalance: Double
)