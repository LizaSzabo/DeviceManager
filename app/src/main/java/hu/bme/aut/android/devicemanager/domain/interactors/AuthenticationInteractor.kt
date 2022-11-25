package hu.bme.aut.android.devicemanager.domain.interactors

import hu.bme.aut.android.devicemanager.data.network.model.LoginNetworkRequest
import hu.bme.aut.android.devicemanager.data.network.model.SignUpRequest
import hu.bme.aut.android.devicemanager.data.network.source.LoginNetworkDataSource
import hu.bme.aut.android.devicemanager.util.NetworkError
import hu.bme.aut.android.devicemanager.util.NetworkResponse
import hu.bme.aut.android.devicemanager.util.NetworkResult
import hu.bme.aut.android.devicemanager.util.UnknownHostError
import javax.inject.Inject

class AuthenticationInteractor @Inject constructor(
    private val loginNetworkDataSource: LoginNetworkDataSource
) {

    suspend fun createUser(userName: String, password: String): NetworkResponse<Unit> {
        val signUpRequest = SignUpRequest(userName, password)
        return loginNetworkDataSource.createUser(signUpRequest)
    }

    suspend fun loginUser(userName: String, password: String): NetworkResponse<Any> {
        val loginRequest = LoginNetworkRequest(userName, password)
        return when (val loginResponse = loginNetworkDataSource.loginUser(loginRequest)) {
            is NetworkError -> {
                NetworkError(loginResponse.errorMessage)
            }
            is NetworkResult -> {
                NetworkResult(loginResponse.result)
            }
            UnknownHostError -> NetworkError("UnknownHostError")
        }
    }
}