package hu.bme.aut.android.devicemanager.domain.interactors

import hu.bme.aut.android.devicemanager.DeviceManagerApp.Companion.currentUser
import hu.bme.aut.android.devicemanager.DeviceManagerApp.Companion.token
import hu.bme.aut.android.devicemanager.data.network.model.LoginNetworkRequest
import hu.bme.aut.android.devicemanager.data.network.model.SignUpRequest
import hu.bme.aut.android.devicemanager.data.network.source.LoginNetworkDataSource
import hu.bme.aut.android.devicemanager.domain.model.User
import hu.bme.aut.android.devicemanager.util.*
import javax.inject.Inject

class AuthenticationInteractor @Inject constructor(
    private val loginNetworkDataSource: LoginNetworkDataSource
) {

    suspend fun createUser(userName: String, password: String): NetworkResponse<Unit> {
        val signUpRequest = SignUpRequest(userName, password)
        return when (val createUserResponse =
            loginNetworkDataSource.createUser(signUpRequest)) {
            is NetworkError -> {
                NetworkError(createUserResponse.errorMessage)
            }
            is NetworkNoResult -> {
                NetworkResult(Unit)
            }
            UnknownHostError -> NetworkError("UnknownHostError")
            is NetworkResult -> {
                NetworkResult(Unit)
            }
        }
    }

    suspend fun loginUser(userName: String, password: String): NetworkResponse<Any> {
        val loginRequest = LoginNetworkRequest(userName, password)
        return when (val loginResponse = loginNetworkDataSource.loginUser(loginRequest)) {
            is NetworkError -> {
                NetworkError(loginResponse.errorMessage)
            }
            is NetworkResult -> {
                token = loginResponse.result.token
                currentUser = User(userName = userName, password = password, id = token)
                NetworkResult(loginResponse.result)
            }
            UnknownHostError -> NetworkError("UnknownHostError")
        }
    }


    suspend fun createAdmin(userName: String, password: String): NetworkResponse<Unit> {
        val signUpRequest = SignUpRequest(userName, password)
        return when (val createAdminResponse =
            loginNetworkDataSource.createAdmin(signUpRequest)) {
            is NetworkError -> {
                NetworkError(createAdminResponse.errorMessage)
            }
            is NetworkNoResult -> {
                NetworkResult(Unit)
            }
            UnknownHostError -> NetworkError("UnknownHostError")
            is NetworkResult -> {
                NetworkResult(Unit)
            }
        }
    }
}