package net.catstack.nfcpay.common.server

import kotlinx.coroutines.runBlocking
import retrofit2.Response
import java.lang.Exception

sealed class Responses<T> {
    data class Successful<T>(val response: T) : Responses<T>()
    data class ServerError<T>(val error: ServerErrorModel) : Responses<T>()
    class InternetError<T> : Responses<T>()
}

fun <T> call(request: suspend () -> Response<ServerResponse<T>>): Responses<T> {
    return runBlocking {
        try {
            val response = request()
            if (response.isSuccessful) {
                return@runBlocking Responses.Successful(response.responseModel)
            } else {
                return@runBlocking Responses.ServerError(response.errorModel)
            }
        } catch (exception: Exception) {
            return@runBlocking Responses.InternetError()
        }
    }
}