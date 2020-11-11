package net.catstack.nfcpay.data

import android.content.SharedPreferences
import com.google.gson.Gson
import net.catstack.nfcpay.domain.TokenModel

class AccountRepository(
    private val gson: Gson,
    private val sharedPreferences: SharedPreferences
) {
    var userToken: TokenModel?
        get() {
            val token = sharedPreferences.getString(::userToken.name, null) ?: return null
            return gson.fromJson(token, TokenModel::class.java)
        }
        set(value) {
            sharedPreferences.edit()
                .putString(::userToken.name, gson.toJson(value))
                .apply()
        }

    fun isUserAuthorized() = userToken != null

    fun logout() {
        sharedPreferences.edit().putString(::userToken.name, null).apply()
    }
}