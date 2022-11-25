package hu.bme.aut.android.devicemanager.data.network.source

import android.util.Log
import hu.bme.aut.android.devicemanager.data.network.api.DeviceManagerApi
import hu.bme.aut.android.devicemanager.data.network.model.SignUpRequest
import hu.bme.aut.android.devicemanager.util.apiCall

class LoginNetworkDataSource(
    private val deviceManagerApi: DeviceManagerApi
) {

    suspend fun createUser(signUpRequest: SignUpRequest) = apiCall{
        deviceManagerApi.createUser(signUpRequest)
    }
}