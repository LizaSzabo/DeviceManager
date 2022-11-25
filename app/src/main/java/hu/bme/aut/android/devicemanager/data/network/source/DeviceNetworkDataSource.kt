package hu.bme.aut.android.devicemanager.data.network.source

import hu.bme.aut.android.devicemanager.DeviceManagerApp.Companion.token
import hu.bme.aut.android.devicemanager.data.network.api.DeviceManagerApi
import hu.bme.aut.android.devicemanager.util.apiCall

class DeviceNetworkDataSource(
    private val deviceManagerApi: DeviceManagerApi
) {
    suspend fun getDevices() = apiCall {
        deviceManagerApi.getDevices(token)
    }
}