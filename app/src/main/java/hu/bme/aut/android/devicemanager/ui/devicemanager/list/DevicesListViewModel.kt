package hu.bme.aut.android.devicemanager.ui.devicemanager.list

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DevicesListViewModel @Inject constructor(
    private val devicesListPresenter: DevicesListPresenter
) : RainbowCakeViewModel<DevicesListViewState>(Initial) {
}