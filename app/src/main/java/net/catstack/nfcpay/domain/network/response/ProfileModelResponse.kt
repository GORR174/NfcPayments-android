package net.catstack.nfcpay.domain.network.response

import net.catstack.nfcpay.domain.CompanyModel
import net.catstack.nfcpay.domain.ProfileModel

data class ProfileModelResponse(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val patronymic: String,
    val email: String,
    val phone: String,
    val registrations: ArrayList<RegistrationModel>
) {
    fun toProfileModel(): ProfileModel {
        val company = registrations[0].company
        return ProfileModel(
            id, firstName, lastName, patronymic, email, phone,
            CompanyModel(
                company.inn,
                company.companyName,
                company.taxSystem,
                company.address,
                company.kkt,
                company.bill.balance
            )
        )
    }
}