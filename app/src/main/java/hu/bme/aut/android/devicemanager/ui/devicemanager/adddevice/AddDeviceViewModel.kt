package hu.bme.aut.android.devicemanager.ui.devicemanager.adddevice

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.devicemanager.util.PresentationNetworkError
import hu.bme.aut.android.devicemanager.util.PresentationNoResult
import hu.bme.aut.android.devicemanager.util.PresentationResult
import javax.inject.Inject

@HiltViewModel
class AddDeviceViewModel @Inject constructor(
    private val addDevicePresenter: AddDevicePresenter
) : RainbowCakeViewModel<AddDeviceViewState>(Initial) {

    fun addNewDevice(deviceName: String) = execute {
        viewState = Loading
        viewState = when (val addNewDeviceResponse = addDevicePresenter.addNewDevice(deviceName)) {
            is PresentationNetworkError -> {
                if (addNewDeviceResponse.message != null) {
                    SaveNewDeviceError(addNewDeviceResponse.message)
                } else {
                    SaveNewDeviceError("Network Error!")
                }
            }
            is PresentationNoResult -> {
                SaveNewDeviceSuccess
            }
            is PresentationResult -> {
                SaveNewDeviceSuccess
            }
        }
    }
}