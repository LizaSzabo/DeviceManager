package hu.bme.aut.android.devicemanager.domain.interactors

import com.auth0.android.jwt.JWT
import hu.bme.aut.android.devicemanager.DeviceManagerApp.Companion.currentUser
import hu.bme.aut.android.devicemanager.DeviceManagerApp.Companion.token
import hu.bme.aut.android.devicemanager.DeviceManagerApp.Companion.userRole
import hu.bme.aut.android.devicemanager.data.network.model.LoginNetworkRequest
import hu.bme.aut.android.devicemanager.data.network.model.SignUpRequest
import hu.bme.aut.android.devicemanager.data.network.source.LoginNetworkDataSource
import hu.bme.aut.android.devicemanager.domain.model.User
import hu.bme.aut.android.devicemanager.util.*
import javax.inject.Inject

class AuthenticationInteractor @Inject constructor(
    private val loginNetworkDataSource: LoginNetworkDataSource
) {

    suspend fun createUser(userName: String, password: String): NetworkResponse<Boolean> {
        val signUpRequest = SignUpRequest(userName, password)
        return when (val createUserResponse =
            loginNetworkDataSource.createUser(signUpRequest)) {
            is NetworkResult -> {
                NetworkResult(true)
            }
            is NetworkError -> {
                NetworkError(createUserResponse.errorMessage)
            }
            UnknownHostError -> NetworkError("NoNetworkError")
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
                val jwt = JWT(token)
                val role: String? = jwt.getClaim("role").asString()
                if (role != userRole.name && userRole == UserRole.ADMIN) {
                    NetworkError("Not an admin user!")
                } else {
                    currentUser = User(userName = userName, password = password, id = token)
                    NetworkResult(loginResponse.result)
                }
            }
            UnknownHostError -> NetworkError("NoNetworkError")
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
            UnknownHostError -> NetworkError("NoNetworkError")
            is NetworkResult -> {
                NetworkResult(Unit)
            }
        }
    }
}