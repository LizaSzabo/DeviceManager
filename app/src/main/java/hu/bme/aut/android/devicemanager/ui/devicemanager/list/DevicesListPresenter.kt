package hu.bme.aut.android.devicemanager.ui.devicemanager.list

import hu.bme.aut.android.devicemanager.domain.interactors.DeviceInteractor
import hu.bme.aut.android.devicemanager.domain.model.Device
import hu.bme.aut.android.devicemanager.util.PresentationResponse
import hu.bme.aut.android.devicemanager.util.makeNetworkCall
import javax.inject.Inject

class DevicesListPresenter @Inject constructor(
    private val deviceInteractor: DeviceInteractor
) {

    suspend fun loadDevices(): PresentationResponse<List<Device>> =
        makeNetworkCall(
            interactor = { deviceInteractor.getDevices() },
            converter = { it }
        )
}