package hu.bme.aut.android.devicemanager.ui.requestmanager.takeback

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.devicemanager.DeviceManagerApp.Companion.mockDeviceData
import hu.bme.aut.android.devicemanager.DeviceManagerApp.Companion.mockRentalRequestData
import hu.bme.aut.android.devicemanager.domain.model.DeviceRentalState
import hu.bme.aut.android.devicemanager.domain.model.RentalRequestStatus
import javax.inject.Inject

@HiltViewModel
class TakeBackViewModel @Inject constructor(
    private val takeBackPresenter: TakeBackPresenter
) : RainbowCakeViewModel<TakeBackViewState>(Initial) {

    fun takeBackDevice(qrCode: String, requestId: String, deviceId: String) {
        val device = mockDeviceData.firstOrNull { it.id == deviceId }
        if (device?.qrCode == qrCode) {
            val rentalRequest = mockRentalRequestData.firstOrNull { it.id == requestId }
            val index = mockRentalRequestData.indexOf(rentalRequest)
            val closedRentalRequest = rentalRequest?.copy(state = RentalRequestStatus.Closed)
            if (closedRentalRequest != null) {
                mockRentalRequestData[index] = closedRentalRequest
            }

            val indexOfDevice = mockDeviceData.indexOf(device)
            val returnedDevice = device.copy(state = DeviceRentalState.Available)
            mockDeviceData[indexOfDevice] = returnedDevice

            viewState = RequestClosedSuccessfully
        } else {
            viewState = NoMatchingQrCode
        }
    }
}