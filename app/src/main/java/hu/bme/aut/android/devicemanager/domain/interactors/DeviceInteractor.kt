package hu.bme.aut.android.devicemanager.domain.interactors

import hu.bme.aut.android.devicemanager.data.network.source.DeviceNetworkDataSource
import hu.bme.aut.android.devicemanager.domain.model.Device
import hu.bme.aut.android.devicemanager.util.*
import javax.inject.Inject

class DeviceInteractor @Inject constructor(
    private val deviceNetworkDataSource: DeviceNetworkDataSource
) {

    suspend fun getDevices(): NetworkResponse<List<Device>> {
        return when (val getDevicesResponse = deviceNetworkDataSource.getDevices()) {
            is NetworkError -> {
                NetworkError(getDevicesResponse.errorMessage)
            }
            is NetworkResult -> {
                val devices = getDevicesResponse.result.map { Device(id = it.id, name = it.name) }
                NetworkResult(devices)
            }
            UnknownHostError -> NetworkError("UnknownHostError")
        }
    }

    suspend fun addDevice(deviceName: String): NetworkResponse<String> {
        return when (val addDevicesResponse = deviceNetworkDataSource.addDevice(deviceName)) {
            is NetworkError -> {
                NetworkError(addDevicesResponse.errorMessage)
            }
            is NetworkNoResult -> {
                NetworkResult("success")
            }
            UnknownHostError -> NetworkError("UnknownHostError")
            is NetworkResult -> {
                NetworkResult("success")
            }
        }
    }
}