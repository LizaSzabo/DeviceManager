package hu.bme.aut.android.devicemanager.util

import retrofit2.HttpException
import java.net.UnknownHostException

sealed class NetworkResponse<out T : Any>

sealed class NetworkNoResult : NetworkResponse<Nothing>()

class NetworkResult<out T : Any>(val result: T) : NetworkResponse<T>()

class NetworkError(val errorMessage: String? = null, val code: Int? = null) : NetworkNoResult()

object UnknownHostError : NetworkNoResult()

/**
 * Executes the given network call and handles the exceptions
 * Wraps the results in a [NetworkResponse]
 */
@Suppress("BlockingMethodInNonBlockingContext")
suspend fun <T : Any> apiCall(block: suspend () -> T): NetworkResponse<T> {
    return try {
        val networkResult = block.invoke()
        NetworkResult(networkResult)
    } catch (unknownHost: UnknownHostException) {
        UnknownHostError
    } catch (httpException: HttpException) {
        val errorMessage = getErrorMessage(httpException)
        val code = httpException.code()
        NetworkError(errorMessage, code)
    }
}

private fun getErrorMessage(httpException: HttpException): String? {
    val beforeString = "error\":\""
    val afterString = "\",\"path"
    return httpException.response()?.errorBody()?.string()?.substringAfter(beforeString)
        ?.substringBefore(afterString)
}


