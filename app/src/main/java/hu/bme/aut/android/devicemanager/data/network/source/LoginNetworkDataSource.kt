package hu.bme.aut.android.devicemanager.data.network.source

import hu.bme.aut.android.devicemanager.DeviceManagerApp.Companion.token
import hu.bme.aut.android.devicemanager.data.network.api.DeviceManagerApi
import hu.bme.aut.android.devicemanager.data.network.model.LoginNetworkRequest
import hu.bme.aut.android.devicemanager.data.network.model.SignUpRequest
import hu.bme.aut.android.devicemanager.util.apiCall

class LoginNetworkDataSource(
    private val deviceManagerApi: DeviceManagerApi
) {

    suspend fun createUser(signUpRequest: SignUpRequest) = apiCall {
        deviceManagerApi.createUser(signUpRequest)
    }

    suspend fun loginUser(loginRequest: LoginNetworkRequest) = apiCall {
        deviceManagerApi.loginUser(loginRequest)
    }

    suspend fun createAdmin(signUpRequest: SignUpRequest) = apiCall {
        deviceManagerApi.createAdmin(token, signUpRequest)
    }
}