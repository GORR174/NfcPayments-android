package net.catstack.nfcpay.common.server

sealed class ResponseStatus<T> {
    data class Successful<T>(val response: T) : ResponseStatus<T>()
    data class ServerError<T>(val error: ServerErrorModel) : ResponseStatus<T>()
    class InternetError<T> : ResponseStatus<T>()
    class Default<T> : ResponseStatus<T>()
    class Loading<T> : ResponseStatus<T>()
}

fun <T> Responses<T>.toResponseStatus(): ResponseStatus<T> {
    return when (this) {
        is Responses.Successful -> ResponseStatus.Successful(this.response)
        is Responses.ServerError -> ResponseStatus.ServerError(this.error)
        is Responses.InternetError -> ResponseStatus.InternetError()
    }
}