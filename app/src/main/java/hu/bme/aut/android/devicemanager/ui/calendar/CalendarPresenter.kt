package hu.bme.aut.android.devicemanager.ui.calendar

import hu.bme.aut.android.devicemanager.domain.interactors.DeviceInteractor
import hu.bme.aut.android.devicemanager.domain.model.ActiveRent
import hu.bme.aut.android.devicemanager.util.PresentationResponse
import hu.bme.aut.android.devicemanager.util.makeNetworkCall
import javax.inject.Inject

class CalendarPresenter @Inject constructor(
    private val deviceInteractor: DeviceInteractor
) {
    suspend fun getActiveRents(deviceId: String): PresentationResponse<List<ActiveRent>> =
        makeNetworkCall(
            interactor = { deviceInteractor.getActiveRents(deviceId) },
            converter = { it }
        )
}