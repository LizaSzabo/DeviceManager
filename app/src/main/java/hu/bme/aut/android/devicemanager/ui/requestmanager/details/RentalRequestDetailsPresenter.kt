package hu.bme.aut.android.devicemanager.ui.requestmanager.details

import hu.bme.aut.android.devicemanager.domain.interactors.RentalRequestInteractor
import hu.bme.aut.android.devicemanager.domain.model.RentalRequest
import hu.bme.aut.android.devicemanager.util.PresentationResponse
import hu.bme.aut.android.devicemanager.util.makeNetworkCall
import javax.inject.Inject

class RentalRequestDetailsPresenter @Inject constructor(
    private val rentalRequestInteractor: RentalRequestInteractor
) {
    suspend fun getRentalRequest(rentalRequestId: String): PresentationResponse<RentalRequest> =
        makeNetworkCall(
            interactor = { rentalRequestInteractor.getRentalRequest(rentalRequestId) },
            converter = { it }
        )
}