package hu.bme.aut.android.devicemanager.ui.requestmanager.list

import hu.bme.aut.android.devicemanager.domain.interactors.RentalRequestInteractor
import hu.bme.aut.android.devicemanager.domain.model.RentalRequest
import hu.bme.aut.android.devicemanager.util.PresentationResponse
import hu.bme.aut.android.devicemanager.util.makeNetworkCall
import javax.inject.Inject

class RequestsListPresenter @Inject constructor(
    private val rentalRequestInteractor: RentalRequestInteractor
) {

    suspend fun loadRentalRequests(): PresentationResponse<List<RentalRequest>> =
        makeNetworkCall(
            interactor = { rentalRequestInteractor.getRentalRequests() },
            converter = { it }
        )
}