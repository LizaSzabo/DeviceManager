package hu.bme.aut.android.devicemanager.ui.requestmanager.details

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.devicemanager.DeviceManagerApp
import hu.bme.aut.android.devicemanager.domain.model.RentalRequestStatus
import javax.inject.Inject


@HiltViewModel
class RentalRequestDetailsViewModel @Inject constructor(
    private val rentalRequestPresenter: RentalRequestDetailsPresenter
) : RainbowCakeViewModel<RentalRequestDetailsViewState>(Initial) {

    fun loadRentalRequestData(rentalRequestId: Long) {
        viewState = RentalRequestDataLoading

        val rentalRequestData =
            DeviceManagerApp.mockRentalRequestData.firstOrNull { it.id == rentalRequestId }

        viewState =
            if (rentalRequestData != null && rentalRequestData.status == RentalRequestStatus.Active) {
                RentalRequestDataReady(rentalRequestData)
            } else if (rentalRequestData != null && rentalRequestData.status == RentalRequestStatus.Accepted) {
                RentalRequestAccepted(rentalRequestData)
            } else {
                RentalRequestLoadingFailure("No such request")
            }
    }

    fun acceptRentalRequest() {
        if (viewState is RentalRequestDataReady) {
            val index =
                DeviceManagerApp.mockRentalRequestData.indexOf((viewState as RentalRequestDataReady).rentalRequest)

            val acceptedRentalRequest =
                (viewState as RentalRequestDataReady).rentalRequest.copy(status = RentalRequestStatus.Accepted)

            DeviceManagerApp.mockRentalRequestData[index] = acceptedRentalRequest

            viewState = if (acceptedRentalRequest != null) {
                RentalRequestAccepted(acceptedRentalRequest)
            } else {
                RentalRequestAcceptFailure("Fails to accept")
            }
        }
    }
}