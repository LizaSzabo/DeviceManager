package hu.bme.aut.android.devicemanager.ui.devicemanager.request

import hu.bme.aut.android.devicemanager.domain.interactors.DeviceInteractor
import hu.bme.aut.android.devicemanager.domain.interactors.RentalRequestInteractor
import hu.bme.aut.android.devicemanager.domain.model.Device
import hu.bme.aut.android.devicemanager.util.PresentationResponse
import hu.bme.aut.android.devicemanager.util.makeNetworkCall
import javax.inject.Inject

class RentalRequestPresenter @Inject constructor(
    private val deviceInteractor: DeviceInteractor,
    private val rentalRequestInteractor: RentalRequestInteractor,
) {

    suspend fun getDevice(deviceId: String): PresentationResponse<Device> =
        makeNetworkCall(
            interactor = { deviceInteractor.getDevice(deviceId) },
            converter = { it }
        )

    suspend fun saveRentalRequest(
        deviceId: String,
        startDate: String,
        endDate: String
    ): PresentationResponse<String> =
        makeNetworkCall(
            interactor = {
                rentalRequestInteractor.saveRentalRequest(
                    deviceId,
                    startDate,
                    endDate
                )
            },
            converter = { it }
        )
}
