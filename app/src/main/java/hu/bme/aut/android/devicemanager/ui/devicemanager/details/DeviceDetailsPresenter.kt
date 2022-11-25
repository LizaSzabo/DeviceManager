package hu.bme.aut.android.devicemanager.ui.devicemanager.details

import hu.bme.aut.android.devicemanager.domain.interactors.DeviceInteractor
import hu.bme.aut.android.devicemanager.domain.model.Device
import hu.bme.aut.android.devicemanager.util.PresentationResponse
import hu.bme.aut.android.devicemanager.util.makeNetworkCall
import javax.inject.Inject

class DeviceDetailsPresenter @Inject constructor(
    private val deviceInteractor: DeviceInteractor
) {

    suspend fun getDevice(deviceId: String): PresentationResponse<Device> =
        makeNetworkCall(
            interactor = { deviceInteractor.getDevice(deviceId) },
            converter = { it }
        )
}