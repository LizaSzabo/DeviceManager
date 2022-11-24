package hu.bme.aut.android.devicemanager.domain.interactors

import hu.bme.aut.android.devicemanager.data.network.source.LoginNetworkDataSource
import hu.bme.aut.android.devicemanager.util.NetworkNoResult
import hu.bme.aut.android.devicemanager.util.NetworkResponse
import hu.bme.aut.android.devicemanager.util.NetworkResult
import javax.inject.Inject

class AuthenticationInteractor @Inject constructor(
    private val loginNetworkDataSource: LoginNetworkDataSource
) {

    suspend fun createUser(userName: String, password: String): NetworkResult<Unit> {
        return when(
            val createUserResponse = loginNetworkDataSource.createUser(userName, password)
        ){
            is NetworkResult -> {
                NetworkResult(createUserResponse.result)
            }
            is NetworkNoResult -> {
                createUserResponse
            }
        }
    }
}