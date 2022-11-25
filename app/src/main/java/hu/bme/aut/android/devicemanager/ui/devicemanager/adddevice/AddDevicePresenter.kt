package hu.bme.aut.android.devicemanager.ui.devicemanager.adddevice

import hu.bme.aut.android.devicemanager.domain.interactors.DeviceInteractor
import hu.bme.aut.android.devicemanager.util.PresentationResponse
import hu.bme.aut.android.devicemanager.util.makeNetworkCall
import javax.inject.Inject

class AddDevicePresenter @Inject constructor(
    private val deviceInteractor: DeviceInteractor
) {

    suspend fun addNewDevice(deviceName: String): PresentationResponse<Unit> =
        makeNetworkCall(
            interactor = { deviceInteractor.addDevice(deviceName) },
            converter = { it }
        )
}