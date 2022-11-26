package hu.bme.aut.android.devicemanager.ui.devicemanager.request

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

import hu.bme.aut.android.devicemanager.util.PresentationNetworkError
import hu.bme.aut.android.devicemanager.util.PresentationResult
import javax.inject.Inject

@HiltViewModel
class RentalRequestViewModel @Inject constructor(
    private val rentalRequestPresenter: RentalRequestPresenter
) : RainbowCakeViewModel<RentalRequestViewState>(Initial) {

    fun loadData(deviceId: String) = execute {
        viewState = Loading

        viewState = when (val deviceData = rentalRequestPresenter.getDevice(deviceId)) {
            is PresentationResult -> {
                DataReady(deviceData.result)
            }
            is PresentationNetworkError -> {
                if (deviceData.message != null) {
                    DataLoadingFailed(deviceData.message)
                } else {
                    DataLoadingFailed("Network error!")
                }
            }
        }
    }

    fun saveRentalRequest(deviceId: String, startDate: String, endDate: String) = execute {
        viewState = Loading

        viewState =
            when (val saveRentalRequestResponse =
                rentalRequestPresenter.saveRentalRequest(deviceId, startDate, endDate)) {
                is PresentationResult -> {
                    RentalRequestSaveSuccess
                }
                is PresentationNetworkError -> {
                    if (saveRentalRequestResponse.message != null) {
                        RentalRequestSaveFailed(saveRentalRequestResponse.message)
                    } else {
                        RentalRequestSaveFailed("Network error!")
                    }
                }
            }
    }
}