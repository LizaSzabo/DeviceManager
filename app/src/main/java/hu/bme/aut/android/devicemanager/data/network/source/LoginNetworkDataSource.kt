package hu.bme.aut.android.devicemanager.data.network.source

import hu.bme.aut.android.devicemanager.data.network.api.DeviceManagerApi
import hu.bme.aut.android.devicemanager.util.apiCall

class LoginNetworkDataSource(
    private val deviceManagerApi: DeviceManagerApi
) {

    suspend fun createUser(userName: String, password: String) = apiCall{

    }
}