package net.catstack.nfcpay.common.server

import com.google.gson.GsonBuilder
import retrofit2.Response

data class ServerErrorResponse(val error: ServerErrorModel)

data class ServerErrorModel(
    val message: String,
    val errorCode: String,
    val httpCode: Int
)

private val gson = GsonBuilder().create()

val Response<*>.errorModel: ServerErrorModel
    get() {
        return gson.fromJson(this.errorBody()?.string(), ServerErrorResponse::class.java).error
    }