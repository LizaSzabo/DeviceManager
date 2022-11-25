package hu.bme.aut.android.devicemanager.ui.devicemanager.details

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.devicemanager.util.PresentationNetworkError
import hu.bme.aut.android.devicemanager.util.PresentationResult
import javax.inject.Inject

@HiltViewModel
class DeviceDetailsViewModel @Inject constructor(
    private val deviceDetailsPresenter: DeviceDetailsPresenter
) : RainbowCakeViewModel<DeviceDetailsViewState>(Initial) {

    fun loadDeviceData(deviceId: String) = execute {
        viewState = DataLoading

        viewState = when (val deviceData = deviceDetailsPresenter.getDevice(deviceId)) {
            is PresentationResult -> {
                DeviceDataReady(deviceData.result)
            }
            is PresentationNetworkError -> {
                if (deviceData.message != null) {
                    DeviceDataLoadingFailure(deviceData.message)
                } else {
                    DeviceDataLoadingFailure("Network error!")
                }
            }
        }
    }
}