package hu.bme.aut.android.devicemanager.domain.interactors

import hu.bme.aut.android.devicemanager.data.network.model.SignUpRequest
import hu.bme.aut.android.devicemanager.data.network.source.LoginNetworkDataSource
import hu.bme.aut.android.devicemanager.util.NetworkNoResult
import hu.bme.aut.android.devicemanager.util.NetworkResponse
import hu.bme.aut.android.devicemanager.util.NetworkResult
import javax.inject.Inject

class AuthenticationInteractor @Inject constructor(
    private val loginNetworkDataSource: LoginNetworkDataSource
) {

    suspend fun createUser(userName: String, password: String): NetworkResponse<String> {
        val signUpRequest = SignUpRequest(userName, password)
        return when (
            val createUserResponse = loginNetworkDataSource.createUser(signUpRequest)
        ) {
            is NetworkResult -> {
                NetworkResult(createUserResponse.result)
            }
            is NetworkNoResult -> {
                createUserResponse
            }
        }
    }
}