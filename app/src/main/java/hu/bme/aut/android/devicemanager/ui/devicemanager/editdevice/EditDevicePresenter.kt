package hu.bme.aut.android.devicemanager.ui.devicemanager.editdevice

import hu.bme.aut.android.devicemanager.domain.interactors.DeviceInteractor
import hu.bme.aut.android.devicemanager.util.PresentationResponse
import hu.bme.aut.android.devicemanager.util.makeNetworkCall
import javax.inject.Inject

class EditDevicePresenter @Inject constructor(
    private val deviceInteractor: DeviceInteractor
) {
    suspend fun editDevice(deviceId: String, deviceName: String): PresentationResponse<Boolean> =
        makeNetworkCall(
            interactor = { deviceInteractor.editDevice(deviceId, deviceName) },
            converter = { it }
        )
}