package hu.bme.aut.android.devicemanager.domain.interactors

import android.util.Log
import hu.bme.aut.android.devicemanager.data.network.source.DeviceNetworkDataSource
import hu.bme.aut.android.devicemanager.domain.model.Device
import hu.bme.aut.android.devicemanager.util.NetworkError
import hu.bme.aut.android.devicemanager.util.NetworkResponse
import hu.bme.aut.android.devicemanager.util.NetworkResult
import hu.bme.aut.android.devicemanager.util.UnknownHostError
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
                Log.i("getDevices", getDevicesResponse.result.toString())
                val devices = getDevicesResponse.result.map { Device(id = it.id, name = it.name) }
                NetworkResult(devices)
            }
            UnknownHostError -> NetworkError("UnknownHostError")
        }
    }
}