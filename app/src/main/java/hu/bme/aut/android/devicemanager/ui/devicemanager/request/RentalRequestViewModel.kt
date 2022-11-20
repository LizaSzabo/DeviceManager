package hu.bme.aut.android.devicemanager.ui.devicemanager.request

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.devicemanager.DeviceManagerApp.Companion.mockDeviceData
import javax.inject.Inject

@HiltViewModel
class RentalRequestViewModel @Inject constructor(
    private val rentalRequestPresenter: RentalRequestPresenter
) : RainbowCakeViewModel<RentalRequestViewState>(Initial) {

    fun loadData(deviceId: Long) {
        viewState = DataLoading

        val device = mockDeviceData.firstOrNull { it.id == deviceId }

        viewState = if (device != null) {
            DataReady(device)
        } else {
            DataLoadingFailed
        }
    }
}