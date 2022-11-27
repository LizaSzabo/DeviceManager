package hu.bme.aut.android.devicemanager.ui.devicemanager.editdevice

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.devicemanager.util.PresentationNetworkError
import hu.bme.aut.android.devicemanager.util.PresentationResult
import javax.inject.Inject

@HiltViewModel
class EditDeviceViewModel @Inject constructor(
    private val editDevicePresenter: EditDevicePresenter
) : RainbowCakeViewModel<EditDeviceViewState>(Initial) {

    fun editDeviceName(deviceId: String, deviceName: String) = execute {
        viewState = Loading
        viewState =
            when (val editDeviceResponse = editDevicePresenter.editDevice(deviceId, deviceName)) {
                is PresentationResult -> {
                    EditDeviceSuccess
                }
                is PresentationNetworkError -> {
                    if (editDeviceResponse.message != null) {
                        EditDeviceError(editDeviceResponse.message)
                    } else {
                        EditDeviceError("Network Error!")
                    }
                }
            }
    }
}