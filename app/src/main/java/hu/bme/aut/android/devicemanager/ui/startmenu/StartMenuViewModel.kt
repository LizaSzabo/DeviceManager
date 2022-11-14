package hu.bme.aut.android.devicemanager.ui.startmenu

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StartMenuViewModel @Inject constructor(
    private val startMenuPresenter: StartMenuPresenter
) : RainbowCakeViewModel<StartMenuViewState>(Initial) {
}