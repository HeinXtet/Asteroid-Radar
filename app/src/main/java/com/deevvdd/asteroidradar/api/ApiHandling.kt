package com.deevvdd.asteroidradar.api

import com.deevvdd.asteroidradar.domain.utils.Either
import retrofit2.Response
import timber.log.Timber
import java.lang.Exception

/**
 * Created by heinhtet deevvdd@gmail.com on 16,June,2021
 */
object ApiHandling {
    suspend fun <T, R> handleApiCall(
        apiCall: suspend () -> Response<T>,
        mapper: suspend (T) -> R
    ): Either<ErrorException, R> {
        return try {
            apiCall().let {
                Timber.d("API Res ${it.toString()}")
                return if (it.body() != null) {
                    Either.Right(mapper(it.body()!!))
                } else {
                    Either.Left(ErrorException.Api(message = "Unknown error on fetching data."))
                }
            }
        } catch (e: Exception) {
            Timber.d("API error ${e.message}")
            Either.Left(ErrorException.Api(message = e.localizedMessage))
        }
    }

}

sealed class ErrorException {
    class Api(val message: String) : ErrorException()
}