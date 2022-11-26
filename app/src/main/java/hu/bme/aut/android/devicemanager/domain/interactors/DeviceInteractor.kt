package hu.bme.aut.android.devicemanager.domain.interactors

import android.util.Log
import hu.bme.aut.android.devicemanager.data.network.source.DeviceNetworkDataSource
import hu.bme.aut.android.devicemanager.domain.model.Device
import hu.bme.aut.android.devicemanager.domain.model.DeviceRentalState
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

    suspend fun getDevice(deviceId: String): NetworkResponse<Device> {
        return when (val getDeviceResponse = deviceNetworkDataSource.getDevice(deviceId)) {
            is NetworkError -> {
                NetworkError(getDeviceResponse.errorMessage)
            }
            is NetworkResult -> {
                val state: DeviceRentalState =
                    if (getDeviceResponse.result.state == "AVAILABLE") DeviceRentalState.Available else DeviceRentalState.Rented
                val device =
                    Device(
                        id = getDeviceResponse.result.id,
                        name = getDeviceResponse.result.name,
                        state = state,
                        calendar = getDeviceResponse.result.calendar
                    )
                NetworkResult(device)
            }
            UnknownHostError -> NetworkError("UnknownHostError")
        }
    }


    suspend fun deleteDevice(deviceId: String): NetworkResponse<Boolean> {
        return when (val deleteDeviceResponse = deviceNetworkDataSource.deleteDevice(deviceId)) {
            is NetworkError -> {
                NetworkError(deleteDeviceResponse.errorMessage)
            }
            is NetworkResult -> {
                NetworkResult(deleteDeviceResponse.result.isSuccessful)
            }
            UnknownHostError -> NetworkError("UnknownHostError")
        }
    }
}