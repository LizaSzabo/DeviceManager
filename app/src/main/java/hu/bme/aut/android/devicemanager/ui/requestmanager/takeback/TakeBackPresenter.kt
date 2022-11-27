package hu.bme.aut.android.devicemanager.ui.requestmanager.takeback

import hu.bme.aut.android.devicemanager.domain.interactors.RentalRequestInteractor
import hu.bme.aut.android.devicemanager.util.PresentationResponse
import hu.bme.aut.android.devicemanager.util.makeNetworkCall
import javax.inject.Inject

class TakeBackPresenter @Inject constructor(
    private val rentalRequestInteractor: RentalRequestInteractor
) {
    suspend fun takeBackRentalRequest(
        rentalRequestId: String,
        comment: String
    ): PresentationResponse<Boolean> =
        makeNetworkCall(
            interactor = {
                rentalRequestInteractor.takeBackRentalRequest(
                    rentalRequestId = rentalRequestId,
                    comment = comment,
                    state = "CLOSED"
                )
            },
            converter = { it }
        )
}