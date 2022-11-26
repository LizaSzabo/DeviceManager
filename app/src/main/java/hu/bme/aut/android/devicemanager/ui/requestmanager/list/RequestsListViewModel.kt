package hu.bme.aut.android.devicemanager.ui.requestmanager.list

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.devicemanager.util.PresentationNetworkError
import hu.bme.aut.android.devicemanager.util.PresentationNoResult
import hu.bme.aut.android.devicemanager.util.PresentationResult
import javax.inject.Inject

@HiltViewModel
class RequestsListViewModel @Inject constructor(
    private val requestsListPresenter: RequestsListPresenter
) : RainbowCakeViewModel<RequestsListViewState>(Initial) {

    fun loadRentalRequests() = execute {
        viewState = RentalRequestsLoading

        viewState = when (val rentalRequestsResponse = requestsListPresenter.loadRentalRequests()) {
            is PresentationNetworkError -> {
                if (rentalRequestsResponse.message != null) {
                    RentalRequestsError(rentalRequestsResponse.message)
                } else {
                    RentalRequestsError("Network Error!")
                }
            }
            is PresentationNoResult -> {
                RentalRequestsReady(listOf())
            }
            is PresentationResult -> {
                RentalRequestsReady(rentalRequestsResponse.result)
            }
        }
    }
}