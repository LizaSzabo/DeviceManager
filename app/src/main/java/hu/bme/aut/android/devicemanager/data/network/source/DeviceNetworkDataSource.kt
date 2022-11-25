package hu.bme.aut.android.devicemanager.data.network.source

import hu.bme.aut.android.devicemanager.DeviceManagerApp.Companion.token
import hu.bme.aut.android.devicemanager.data.network.api.DeviceManagerApi
import hu.bme.aut.android.devicemanager.data.network.model.AddDeviceRequest
import hu.bme.aut.android.devicemanager.util.apiCall

class DeviceNetworkDataSource(
    private val deviceManagerApi: DeviceManagerApi
) {
    suspend fun getDevices() = apiCall {
        deviceManagerApi.getDevices(token)
    }

    suspend fun addDevice(deviceName: String) = apiCall {
        val addDeviceRequest = AddDeviceRequest(name = deviceName, state = "")
        deviceManagerApi.addDevice(token, addDeviceRequest)
    }

    suspend fun getDevice(deviceId: String) = apiCall {
        deviceManagerApi.getDevice(deviceId, token)
    }
}