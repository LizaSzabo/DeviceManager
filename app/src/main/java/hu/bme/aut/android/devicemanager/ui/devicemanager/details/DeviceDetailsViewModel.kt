package hu.bme.aut.android.devicemanager.ui.devicemanager.details

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.devicemanager.DeviceManagerApp.Companion.mockDeviceData
import javax.inject.Inject

@HiltViewModel
class DeviceDetailsViewModel @Inject constructor(
    private val deviceDetailsPresenter: DeviceDetailsPresenter
) : RainbowCakeViewModel<DeviceDetailsViewState>(Initial) {

    fun loadDeviceData(deviceId: Long) {
        viewState = DataLoading

        val deviceData = mockDeviceData.firstOrNull { it.id == deviceId }

        viewState = if (deviceData != null) {
            DeviceDataReady(deviceData)
        } else {
            DeviceDataLoadingFailure("No such device")
        }

    }
}