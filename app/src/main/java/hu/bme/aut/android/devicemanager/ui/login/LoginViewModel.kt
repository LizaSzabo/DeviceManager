package hu.bme.aut.android.devicemanager.ui.login

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginPresenter: LoginPresenter
) : RainbowCakeViewModel<LoginViewState>(InitialUser) {

    fun switchToUserMode() {
        viewState = InitialUser
    }

    fun switchToAdminMode() {
        viewState = InitialAdmin
    }

}