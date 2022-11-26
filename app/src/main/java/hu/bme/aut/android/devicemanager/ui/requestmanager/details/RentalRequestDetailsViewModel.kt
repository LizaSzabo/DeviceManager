package hu.bme.aut.android.devicemanager.ui.requestmanager.details

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.devicemanager.domain.model.RentalRequestStatus
import hu.bme.aut.android.devicemanager.util.PresentationNetworkError
import hu.bme.aut.android.devicemanager.util.PresentationResult
import javax.inject.Inject


@HiltViewModel
class RentalRequestDetailsViewModel @Inject constructor(
    private val rentalRequestPresenter: RentalRequestDetailsPresenter
) : RainbowCakeViewModel<RentalRequestDetailsViewState>(Initial) {

    fun loadRentalRequestData(rentalRequestId: String) = execute {
        viewState = RentalRequestDataLoading

        viewState = when (val rentalRequestData =
            rentalRequestPresenter.getRentalRequest(rentalRequestId)) {
            is PresentationResult -> {
                when (rentalRequestData.result.state) {
                    RentalRequestStatus.Active -> {
                        RentalRequestDataReady(rentalRequestData.result)
                    }
                    RentalRequestStatus.Accepted -> {
                        RentalRequestAccepted(rentalRequestData.result)
                    }
                    RentalRequestStatus.Closed -> {
                        RentalRequestClosed(rentalRequestData.result)
                    }
                }
            }
            is PresentationNetworkError -> {
                if (rentalRequestData.message != null) {
                    RentalRequestLoadingFailure(rentalRequestData.message)
                } else {
                    RentalRequestLoadingFailure("Network error!")
                }
            }
        }
    }

    fun acceptRentalRequest() {
    }
}