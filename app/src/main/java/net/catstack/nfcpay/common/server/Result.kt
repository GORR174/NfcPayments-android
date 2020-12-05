package net.catstack.nfcpay.common.server

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import net.catstack.nfcpay.common.aliases.ServerResponse
import java.lang.Exception

sealed class Result<out T : Any> {
    object Loading : Result<Nothing>()
    class Success<out T : Any>(val data: T) : Result<T>()
    class ServerError(val serverError: ServerErrorModel) : Result<Nothing>()
    object InternetError : Result<Nothing>()
}

fun <T : Any> ServerResponse<T>.getResult(): Result<T> {
    if (this.body() != null) {
        return Result.Success(this.body()!!.response)
    } else if (this.errorBody() != null) {
        return Result.ServerError(this.errorModel)
    }
    throw Exception("Unhandled exception")
}

fun <T : Any> Flow<Result<T>>.postToLiveData(liveData: MutableLiveData<Result<T>>) =
    this.onStart { liveData.postValue(Result.Loading) }
        .catch { liveData.postValue(Result.InternetError) }