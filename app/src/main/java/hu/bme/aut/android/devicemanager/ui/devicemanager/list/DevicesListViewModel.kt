package hu.bme.aut.android.devicemanager.ui.devicemanager.list

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.devicemanager.util.PresentationNetworkError
import hu.bme.aut.android.devicemanager.util.PresentationNoResult
import hu.bme.aut.android.devicemanager.util.PresentationResult
import javax.inject.Inject

@HiltViewModel
class DevicesListViewModel @Inject constructor(
    private val devicesListPresenter: DevicesListPresenter
) : RainbowCakeViewModel<DevicesListViewState>(Initial) {

    fun loadDevices() = execute {
        viewState = Loading
        viewState = when (val devicesResponse = devicesListPresenter.loadDevices()) {
            is PresentationNetworkError -> {
                if (devicesResponse.message != null) {
                    LoadingError(devicesResponse.message)
                } else {
                    LoadingError("Network Error!")
                }
            }
            is PresentationNoResult -> {
                DataReady(listOf())
            }
            is PresentationResult -> {
                DataReady(devicesResponse.result)
            }
        }
    }

    fun deleteDevice(deviceId: String) = execute {
        viewState = Loading
        viewState = when (val deleteResult = devicesListPresenter.deleteDevice(deviceId)) {
            is PresentationNetworkError -> {
                if (deleteResult.message != null) {
                    DeleteError(deleteResult.message)
                } else {
                    DeleteError("Network Error!")
                }
            }
            is PresentationNoResult -> {
                DeleteSuccess
            }
            is PresentationResult -> {
                DeleteSuccess
            }
        }
    }
}