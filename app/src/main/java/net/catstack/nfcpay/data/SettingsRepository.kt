package net.catstack.nfcpay.data

import android.content.SharedPreferences
import com.google.gson.Gson
import net.catstack.nfcpay.domain.TokenModel

class SettingsRepository(
    private val gson: Gson,
    private val sharedPreferences: SharedPreferences
) {
    enum class ProfileMode {
        CUSTOMER,
        SELLER,
    }

    var profileMode: ProfileMode
        get() {
            val mode = sharedPreferences.getString(::profileMode.name, null) ?: return ProfileMode.CUSTOMER
            return gson.fromJson(mode, ProfileMode::class.java)
        }
        set(value) {
            sharedPreferences.edit()
                .putString(::profileMode.name, gson.toJson(value))
                .apply()
        }
}