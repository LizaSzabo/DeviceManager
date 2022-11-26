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
        viewState = DataLoading

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
}