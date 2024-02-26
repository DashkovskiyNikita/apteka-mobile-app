package com.example.apteka_mobile_app.data

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

sealed class NetworkResponse<out S : Any, out E> {
    /**
     * Success response with body
     */
    data class Success<T : Any>(val body: T) : NetworkResponse<T, Nothing>()

    /**
     * Failure response with body
     */
    data class ApiError<U>(val body: U, val code: Int) : NetworkResponse<Nothing, U>()

    /**
     * Network error
     */
    data class NetworkError(val error: IOException) : NetworkResponse<Nothing, Nothing>()

    /**
     * For example, json parsing error
     */
    data class UnknownError(val error: Throwable) : NetworkResponse<Nothing, Nothing>()
}

fun <E> handleNetworkException(throwable: Throwable): NetworkResponse<Nothing, E> {
    return when (throwable) {
        is HttpException -> {
            val response = throwable.response()
                ?: return NetworkResponse.UnknownError(throwable)
            val errorBody = response.errorBody()
            runCatching {
                val type = object : TypeToken<E>() {}.type
                val errorResponse: E = Gson().fromJson(errorBody?.charStream(), type)
                NetworkResponse.ApiError(body = errorResponse, code = response.code())
            }.getOrElse {
                NetworkResponse.UnknownError(throwable)
            }
        }

        is IOException -> {
            NetworkResponse.NetworkError(throwable)
        }

        else -> NetworkResponse.UnknownError(throwable)
    }
}

suspend fun <E : Any> networkCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    apiCall: suspend () -> E
): NetworkResponse<E, Nothing> = withContext(dispatcher) {
    runCatching {
        NetworkResponse.Success(apiCall())
    }.getOrElse(::handleNetworkException)
}

