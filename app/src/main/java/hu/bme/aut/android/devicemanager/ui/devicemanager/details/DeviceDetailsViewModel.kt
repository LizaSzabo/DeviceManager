package hu.bme.aut.android.devicemanager.ui.devicemanager.details

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DeviceDetailsViewModel @Inject constructor(
    private val deviceDetailsPresenter: DeviceDetailsPresenter
) : RainbowCakeViewModel<DeviceDetailsViewState>(Initial) {
}