package hu.bme.aut.android.devicemanager.ui.startmenu

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.devicemanager.ui.login.InitialUser
import hu.bme.aut.android.devicemanager.ui.login.LoginPresenter
import hu.bme.aut.android.devicemanager.ui.login.LoginViewState
import javax.inject.Inject

@HiltViewModel
class StartMenuViewModel @Inject constructor(
    private val startMenuPresenter: StartMenuPresenter
) : RainbowCakeViewModel<StartMenuViewState>(Initial) {
}