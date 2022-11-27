package hu.bme.aut.android.devicemanager.data.network.source

import hu.bme.aut.android.devicemanager.DeviceManagerApp.Companion.token
import hu.bme.aut.android.devicemanager.data.network.api.DeviceManagerApi
import hu.bme.aut.android.devicemanager.data.network.model.AddDeviceRequest
import hu.bme.aut.android.devicemanager.data.network.model.EditDeviceRequest
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

    suspend fun deleteDevice(deviceId: String) = apiCall {
        deviceManagerApi.deleteDevice(token, deviceId)
    }

    suspend fun editDevice(deviceId: String, deviceName: String) = apiCall {
        val editDeviceRequest = EditDeviceRequest(name = deviceName)
        deviceManagerApi.editDevice(token, deviceId, editDeviceRequest)
    }
}